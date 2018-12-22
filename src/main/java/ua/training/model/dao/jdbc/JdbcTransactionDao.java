package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.exception.UnsupportedOperationException;
import ua.training.model.service.producers.TransactionProducer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public long makeTransaction(Transaction transaction) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"), PreparedStatement.RETURN_GENERATED_KEYS)) {

                Account sender = getAccountById(transaction.getSender(), getAccountStatement);

                updateAccountBalance(sender.getId(), sender.withdrawFromAccount(transaction), updateBalanceStatement);

                Account receiver = getAccountById(transaction.getReceiver(), getAccountStatement);

                updateAccountBalance(receiver.getId(), receiver.replenishAccount(transaction), updateBalanceStatement);

                setStatementParameters(transaction, insertTransactionStatement);
                insertTransactionStatement.executeUpdate();

                long transactionId = extractGeneratedKey(insertTransactionStatement.getGeneratedKeys());

                connection.commit();

                return transactionId;
            } catch (SQLException | NonActiveAccountException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            } catch (NotEnoughMoneyException exception) {
                connection.rollback();

                logger.error(exception);
                throw exception;
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public long makeTransaction(Long accountId, TransactionProducer producer) throws CancelingTaskException{
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateBalanceStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.balance"));
                 PreparedStatement insertTransactionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.insert"), PreparedStatement.RETURN_GENERATED_KEYS)) {
                Account account = getAccountById(accountId, getAccountStatement);

                Optional<Transaction> optionalTransaction = producer.produce(account);

                Transaction transaction;

                if (optionalTransaction.isPresent())  {
                    transaction = optionalTransaction.get();
                } else {
                    throw new SQLException();
                }

                updateAccountBalance(account.getId(), account.getBalance(), updateBalanceStatement);

                setStatementParameters(transaction, insertTransactionStatement);
                insertTransactionStatement.executeUpdate();

                long transactionId = extractGeneratedKey(insertTransactionStatement.getGeneratedKeys());

                connection.commit();

                return transactionId;
            } catch (SQLException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            } catch (CancelingTaskException exception) {
                connection.rollback();

                logger.error(exception);
                throw new CancelingTaskException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
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

    private void updateAccountBalance(Long accountId, BigDecimal balance, PreparedStatement updateBalanceStatement) throws SQLException {
        updateBalanceStatement.setBigDecimal(1, balance);
        updateBalanceStatement.setLong(2, accountId);
        updateBalanceStatement.executeUpdate();
    }

    private void setStatementParameters(Transaction entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, entity.getSender());
        preparedStatement.setLong(2, entity.getReceiver());
        preparedStatement.setString(3, entity.getType().name());
        preparedStatement.setBigDecimal(4, entity.getAmount());
        preparedStatement.setString(5, entity.getCurrency().name());
    }

    private long extractGeneratedKey(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong(1);
        } else {
            throw new SQLException();
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
     */
    @Override
    public Long insert(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method not supported, because transactions are immutable after insertion.
     */
    @Override
    public int update(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method not supported, because transactions are fix operations history.
     */
    @Override
    public int remove(Transaction entity) {
        throw new UnsupportedOperationException();
    }
}
