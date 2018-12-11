package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.exception.UnsupportedOperationException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Realization of {@link TransactionDao} for database source using jdbc library.
 * @see TransactionDao
 * @see ua.training.model.dao.Dao
 * @see Transaction
 * @author Oleksii Shevchenko
 */
public class JdbcTransactionDao implements TransactionDao {
    private static Logger logger = LogManager.getLogger(JdbcTransactionDao.class);

    /**
     * Returns all transactions where account is sender or receiver.
     * @param accountId Targeted account.
     * @return List of account transactions.
     */
    @Override
    public List<Transaction> getAccountTransactions(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.get.by.account"))) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Transaction> mapper = new JdbcMapperFactory().getTransactionMapper();

            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                transactions.add(mapper.map(resultSet));
            }

            return transactions;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Transfer money from one account to another if it is possible. If it is not possible throws exception.
     * @param senderId Account from witch will be withdrawn money.
     * @param receiverId Account to witch will be putted money.
     * @param amount Amount of money that are transferred.
     * @param currency Transaction currency.
     */
    @Override
    public void makeTransfer(Long senderId, Long receiverId, BigDecimal amount, Currency currency) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"))) {
                getAccountStatement.setLong(1, senderId);

                ResultSet resultSet = getAccountStatement.executeQuery();
                Mapper<Account> mapper = new JdbcMapperFactory().getAccountMapper();

                Account sender;
                if (resultSet.next()) {
                    sender = mapper.map(resultSet);
                } else {
                    throw new SQLException();
                }

                getAccountStatement.setLong(1, receiverId);

                resultSet = getAccountStatement.executeQuery();

                Account receiver;
                if (resultSet.next()) {
                    receiver = mapper.map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!(sender.isActive() && receiver.isActive())) {
                    throw new SQLException();
                }

                CurrencyExchangeService exchangeService = new CurrencyExchangeService();

                sender.withdrawFromAccount(amount.multiply(exchangeService.exchangeRate(currency, sender.getCurrency())));
                receiver.refillAccount(amount.multiply(exchangeService.exchangeRate(currency, receiver.getCurrency())));

                updateBalanceStatement.setBigDecimal(1, sender.getBalance());
                updateBalanceStatement.setLong(2, sender.getId());
                updateBalanceStatement.executeUpdate();

                updateBalanceStatement.setBigDecimal(1, receiver.getBalance());
                updateBalanceStatement.setLong(2, receiver.getId());
                updateBalanceStatement.executeUpdate();

                insertTransactionStatement.setLong(1, sender.getId());
                insertTransactionStatement.setLong(2, receiver.getId());
                insertTransactionStatement.setString(3, Transaction.Type.MANUAL.name());
                insertTransactionStatement.setBigDecimal(4, amount);
                insertTransactionStatement.setString(5, currency.name());
                insertTransactionStatement.executeUpdate();
            } catch (SQLException | NotEnoughMoneyException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    /**
     * This method used by system for periodic update of account balance to support its account policy (accrual of
     * interest on a deposit or a loan).
     * @param accountId Targeted account.
     * @param updater Updater service.
     */
    @Override
    public void makeUpdate(Long accountId, Function<Account, Transaction> updater) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"))) {
                getAccountStatement.setLong(1, accountId);

                ResultSet resultSet = getAccountStatement.executeQuery();

                Account account;
                if (resultSet.next()) {
                    account = new JdbcMapperFactory().getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!account.isActive()) {
                    throw new SQLException();
                }

                Transaction transaction = updater.apply(account);

                updateAccountStatement.setBigDecimal(1, account.getBalance());
                updateAccountStatement.setLong(2, accountId);
                updateAccountStatement.executeUpdate();

                insertTransactionStatement.setNull(1, Types.BIGINT);
                insertTransactionStatement.setLong(2, transaction.getReceiver());
                insertTransactionStatement.setString(3, Transaction.Type.REGULAR.name());
                insertTransactionStatement.setBigDecimal(4, transaction.getAmount());
                insertTransactionStatement.setString(5, transaction.getCurrency().name());
                insertTransactionStatement.executeUpdate();
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

    /**
     * This method is used by admins to refill account balance from external sources (example: real payment in bank).
     * @param accountId Targeted account.
     * @param amount Amount of refilling.
     * @param currency Currency of refilling.
     */
    @Override
    public void makeRefill(Long accountId, BigDecimal amount, Currency currency) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"))) {
                getAccountStatement.setLong(1, accountId);

                ResultSet resultSet = getAccountStatement.executeQuery();

                Account account;
                if (resultSet.next()) {
                    account = new JdbcMapperFactory().getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (!account.isActive()) {
                    throw new SQLException();
                }

                BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(currency, account.getCurrency());

                updateBalanceStatement.setBigDecimal(1, account.refillAccount(amount.multiply(exchangeRate)));
                updateBalanceStatement.setLong(2, accountId);
                updateBalanceStatement.executeUpdate();

                insertTransactionStatement.setNull(1, Types.BIGINT);
                insertTransactionStatement.setLong(2, account.getId());
                insertTransactionStatement.setString(3, Transaction.Type.EXTERNAL.name());
                insertTransactionStatement.setBigDecimal(4, amount);
                insertTransactionStatement.setString(5, currency.name());
                insertTransactionStatement.executeUpdate();
            } catch (SQLException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }

    }

    @Override
    public Transaction get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getTransactionMapper().map(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method is not supported, because transaction insertion possible only in transact operations
     * ({@link TransactionDao#makeRefill(Long, BigDecimal, Currency)}, {@link TransactionDao#makeTransfer(Long, Long, BigDecimal, Currency)} and etc.)
     */
    @Override
    public Long insert(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method not supported, because transactions are immutable after insertion.
     */
    @Override
    public void update(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method not supported, because transactions are fix operations history.
     */
    @Override
    public void remove(Transaction entity) {
        throw new UnsupportedOperationException();
    }
}
