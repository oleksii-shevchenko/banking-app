package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.UnsupportedOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {
    private static Logger logger = LogManager.getLogger(JdbcTransactionDao.class);

    @Override
    public List<Transaction> getAllTransactionsForAccount(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.get.by.account"))) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, accountId);
            return createListFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
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
                throw new RuntimeException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Transaction> get(List<Long> keys) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.transactions.get.by.id"))) {
                for (Long key : keys) {
                    preparedStatement.setLong(1, key);
                    preparedStatement.addBatch();
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                connection.commit();

                return createListFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    //todo add comment that insert possible only in transact operations
    @Override
    public Long insert(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    //todo add comment that transaction records is immutable in db
    @Override
    public void update(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    //todo add comment that transaction records is immutable in db
    @Override
    public void remove(Transaction entity) {
        throw new UnsupportedOperationException();
    }

    private List<Transaction> createListFromResultSet(ResultSet resultSet) throws SQLException {
        Mapper<Transaction, ResultSet> mapper = new JdbcMapperFactory().getTransactionMapper();

        List<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            transactions.add(mapper.map(resultSet));
        }

        return transactions;
    }
}
