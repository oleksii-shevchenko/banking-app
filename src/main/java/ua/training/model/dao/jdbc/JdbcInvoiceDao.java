package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.InvoiceDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.exception.UnsupportedOperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization of {@link InvoiceDao} for database source using jdbc library.
 * @see ua.training.model.dao.Dao
 * @see Invoice
 * @see ua.training.model.dao.InvoiceDao
 * @author Oleksii Shevchenko
 */
public class JdbcInvoiceDao implements InvoiceDao {
    private static Logger logger = LogManager.getLogger(JdbcInvoiceDao.class);

    /**
     * Method returns all invoices where specified account is requester.
     * @param accountId Targeted account.
     * @return List of invoices.
     */
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

    /**
     * Method returns all invoices where specified account is payer.
     * @param accountId Targeted account.
     * @return List of invoices.
     */
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

    /**
     * Method accepts invoice and makes payment base on this invoice.
     * @param invoiceId Targeted invoice.
     */
    @Override
    public void acceptInvoice(Long invoiceId) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement getInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.get.by.id"));
                 PreparedStatement updateInvoiceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.invoices.update.transaction"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"))) {

                Invoice invoice = getInvoiceById(invoiceId, getInvoiceStatement);

                if (!invoice.getStatus().equals(Invoice.Status.PROCESSING)) {
                    throw new SQLException();
                }

                Account requester = getAccountById(invoice.getRequester(), getAccountStatement);
                Account payer = getAccountById(invoice.getPayer(), getAccountStatement);

                Transaction transaction = Transaction.getBuilder()
                        .setSender(invoice.getPayer())
                        .setReceiver(invoice.getRequester())
                        .setType(Transaction.Type.MANUAL)
                        .setAmount(invoice.getAmount())
                        .setCurrency(invoice.getCurrency())
                        .build();

                updateBalanceStatement.setBigDecimal(1, requester.replenishAccount(transaction));
                updateBalanceStatement.setLong(2, requester.getId());
                updateBalanceStatement.executeUpdate();

                updateBalanceStatement.setBigDecimal(1, payer.withdrawFromAccount(transaction));
                updateBalanceStatement.setLong(2, payer.getId());
                updateBalanceStatement.executeUpdate();

                insertTransactionStatement.setLong(1, transaction.getSender());
                insertTransactionStatement.setLong(2, transaction.getReceiver());
                insertTransactionStatement.setString(3, transaction.getType().name());
                insertTransactionStatement.setBigDecimal(4, transaction.getAmount());
                insertTransactionStatement.setString(5, transaction.getCurrency().name());
                insertTransactionStatement.executeUpdate();

                ResultSet resultSet = insertTransactionStatement.getGeneratedKeys();

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
            } catch (SQLException | NotEnoughMoneyException | NonActiveAccountException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    private Account getAccountById(Long accountId, PreparedStatement getAccountStatement) throws SQLException {
        getAccountStatement.setLong(1, accountId);

        ResultSet resultSet = getAccountStatement.executeQuery();

        if (resultSet.next()) {
            return new JdbcMapperFactory().getAccountMapper().map(resultSet);
        } else {
            throw new SQLException();
        }
    }

    /**
     * Method denys specified invoice.
     * @param invoiceId Targeted invoice.
     */
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
                    throw new SQLException();
                }

                updateInvoiceStatement.setString(1, Invoice.Status.DENIED.name());
                updateInvoiceStatement.setLong(2, invoiceId);
                updateInvoiceStatement.executeUpdate();

                connection.commit();
            } catch (SQLException exception) {
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
            return getInvoiceById(key, preparedStatement);
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    private Invoice getInvoiceById(Long invoiceId, PreparedStatement getAccountStatement) throws SQLException {
        getAccountStatement.setLong(1, invoiceId);

        ResultSet resultSet = getAccountStatement.executeQuery();

        if (resultSet.next()) {
            return new JdbcMapperFactory().getInvoiceMapper().map(resultSet);
        } else {
            throw new SQLException();
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
    public int update(Invoice entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remove(Invoice entity) {
        throw new UnsupportedOperationException();
    }
}
