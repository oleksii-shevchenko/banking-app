package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.InvoiceDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.dao.mapper.factory.MapperFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CompletedInvoiceException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.exception.UnsupportedOperationException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcInvoiceDao implements InvoiceDao {
    private static Logger logger = LogManager.getLogger(JdbcInvoiceDao.class);

    @Override
    public List<Invoice> getInvoicesByRequester(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.get.by.requester"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Invoice> mapper = new JdbcMapperFactory().getInvoiceMapper();

            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoices.add(mapper.map(resultSet));
            }

            return invoices;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Invoice> getInvoicesByPayer(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.get.by.payer"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Invoice> mapper = new JdbcMapperFactory().getInvoiceMapper();

            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoices.add(mapper.map(resultSet));
            }

            return invoices;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void acceptInvoice(Long invoiceId) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement getInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.get.by.id"));
                 PreparedStatement updateInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.update.transaction"), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"));
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"))) {
                MapperFactory mapperFactory = new JdbcMapperFactory();

                getInvoiceStatement.setLong(1, invoiceId);

                ResultSet resultSet = getInvoiceStatement.executeQuery();

                Invoice invoice;
                if (resultSet.next()) {
                    invoice = mapperFactory.getInvoiceMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!invoice.getStatus().equals(Invoice.Status.PROCESSING)) {
                    throw new CompletedInvoiceException();
                }

                getAccountStatement.setLong(1, invoice.getRequester());

                resultSet = getAccountStatement.executeQuery();

                Account requester;
                if (resultSet.next()) {
                    requester = mapperFactory.getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                getAccountStatement.setLong(1, invoice.getPayer());

                resultSet = getAccountStatement.executeQuery();

                Account payer;
                if (resultSet.next()) {
                    payer = mapperFactory.getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!(requester.isActive() && payer.isActive())) {
                    throw new SQLException();
                }

                CurrencyExchangeService exchangeService = new CurrencyExchangeService();
                BigDecimal exchangeRate;

                exchangeRate = exchangeService.exchangeRate(invoice.getCurrency(), requester.getCurrency());
                updateBalanceStatement.setBigDecimal(1, requester.refillAccount(invoice.getAmount().multiply(exchangeRate)));
                updateBalanceStatement.setLong(2, requester.getId());
                updateBalanceStatement.executeUpdate();

                exchangeRate = exchangeService.exchangeRate(invoice.getCurrency(), payer.getCurrency());
                updateBalanceStatement.setBigDecimal(1, payer.withdrawFromAccount(invoice.getAmount().multiply(exchangeRate)));
                updateBalanceStatement.setLong(2, payer.getId());

                insertTransactionStatement.setLong(1, payer.getId());
                insertTransactionStatement.setLong(2, requester.getId());
                insertTransactionStatement.setString(3, Transaction.Type.MANUAL.name());
                insertTransactionStatement.setBigDecimal(4, invoice.getAmount());
                insertTransactionStatement.setString(5, invoice.getCurrency().name());
                insertTransactionStatement.executeUpdate();

                resultSet = insertTransactionStatement.getGeneratedKeys();

                long transactionId;
                if (resultSet.next()) {
                    transactionId = resultSet.getLong(1);
                } else {
                    throw new SQLException();
                }

                updateInvoiceStatement.setString(1, Invoice.Status.ACCEPTED.name());
                updateInvoiceStatement.setLong(2, transactionId);
                updateInvoiceStatement.setLong(3, invoiceId);
                updateInvoiceStatement.executeUpdate();

                connection.commit();
            } catch (SQLException | CompletedInvoiceException | NotEnoughMoneyException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    @Override
    public void denyInvoice(Long invoiceId) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoice.get.by.id"));
                 PreparedStatement updateInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoice.update.status"))) {
                getInvoiceStatement.setLong(1, invoiceId);

                ResultSet resultSet = getInvoiceStatement.executeQuery();

                Invoice invoice;
                if (resultSet.next()) {
                    invoice = new JdbcMapperFactory().getInvoiceMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!(invoice.getStatus().equals(Invoice.Status.PROCESSING))) {
                    throw new CompletedInvoiceException();
                }

                updateInvoiceStatement.setString(1, Invoice.Status.DENIED.name());
                updateInvoiceStatement.setLong(2, invoiceId);
                updateInvoiceStatement.executeUpdate();

                connection.commit();
            } catch (SQLException | CompletedInvoiceException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    @Override
    public Invoice get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getInvoiceMapper().map(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Invoice entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.insert"), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getRequester());
            preparedStatement.setLong(2, entity.getPayer());
            preparedStatement.setBigDecimal(3, entity.getAmount());
            preparedStatement.setString(4, entity.getCurrency().name());
            preparedStatement.setString(5, entity.getStatus().name());
            preparedStatement.setString(6, entity.getDescription());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(Invoice entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Invoice entity) {
        throw new UnsupportedOperationException();
    }
}
