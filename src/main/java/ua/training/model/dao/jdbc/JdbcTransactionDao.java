package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.TransactionDao;
import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public class JdbcTransactionDao implements TransactionDao {
    private static Logger logger = LogManager.getLogger(JdbcTransactionDao.class);

    @Override
    public List<Transaction> getAccountTransactions(Long accountId) {
        return null;
    }

    @Override
    public void makeTransfer(Long senderId, Long receiverId, BigDecimal amount) {

    }

    @Override
    public void makeUpdate(Long accountId, Consumer<Account> updater) {

    }

    @Override
    public void makeRefill(Long accountId, BigDecimal amount) {

    }

    @Override
    public Transaction get(Long key) {
        return null;
    }

    @Override
    public Long insert(Transaction entity) {
        return null;
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void remove(Transaction entity) {

    }

    /*
    @Override
    public List<Transaction> getAllAccountTransactions(Long accountId) {
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
    } */
}
