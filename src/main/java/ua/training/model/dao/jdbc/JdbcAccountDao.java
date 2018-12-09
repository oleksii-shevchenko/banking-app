package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.AccountDao;
import ua.training.model.entity.Account;

import java.util.List;

public class JdbcAccountDao implements AccountDao {
    private static Logger logger = LogManager.getLogger(JdbcAccountDao.class);

    @Override
    public List<Long> getUserAccountsIds(Long userId) {
        return null;
    }

    @Override
    public List<Account> getUserAccounts(Long userId) {
        return null;
    }

    @Override
    public void createAccount(Long userId, Account account) {

    }

    @Override
    public void blockAccount(Long accountId) {

    }

    @Override
    public void closeAccount(Long userId, Long accountId) {

    }

    @Override
    public Account get(Long key) {
        return null;
    }

    @Override
    public Long insert(Account entity) {
        return null;
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void remove(Account entity) {

    }

    /*
    private static Map<String, StatementStrategy> strategies;

    static {
        strategies = new HashMap<>();
        strategies.put("DepositAccount", new DepositStatementStrategy());
        strategies.put("CreditAccount", new CreditStatementStrategy());
    }

    @Override
    public List<Long> getAllUserAccountsIds(Long userId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.id.by.user"))) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Long> accountsIds = new ArrayList<>();
            while (resultSet.next()) {
                accountsIds.add(resultSet.getLong("account_id"));
            }

            return accountsIds;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Account> getAllUserAccounts(Long userId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.account.by.user"))) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Account, ResultSet> mapper = new JdbcMapperFactory().getAccountMapper();

            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(mapper.map(resultSet));
            }

            return accounts;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void createAccount(Long userId, Account account) {
        StatementStrategy strategy = strategies.get(account.getClass().getSimpleName());

        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);

            try (PreparedStatement insertAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.insert"), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertHolderStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.insert"))) {
                strategy.setStatementParameters(account, insertAccountStatement);
                insertAccountStatement.executeUpdate();

                ResultSet resultSet = insertAccountStatement.getGeneratedKeys();

                long accountId;
                if (resultSet.next()) {
                    accountId = resultSet.getLong(1);
                } else {
                    throw new SQLException();
                }

                insertHolderStatement.setLong(1, userId);
                insertHolderStatement.setLong(2, accountId);
                insertHolderStatement.setString(3, Permission.ALL.name());
                insertHolderStatement.executeUpdate();

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
    public void closeAccount(Long userId, Long accountId) {

    }

    @Override
    public void makeTransfer(Long senderId, Long receiverId, BigDecimal amount) {

    }

    @Override
    public void makeUpdate(Long accountId, Consumer<Account> updater) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);

            try (PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement updateAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update"))) {
                getAccountStatement.setLong(1, accountId);

                ResultSet resultSet = getAccountStatement.executeQuery();

                Account account;
                if (resultSet.next()) {
                    account = new JdbcMapperFactory().getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                StatementStrategy strategy = strategies.get(account.getClass().getSimpleName());

                updater.accept(account);

                strategy.setStatementParameters(account, updateAccountStatement);
                updateAccountStatement.setLong(12, accountId);
                updateAccountStatement.executeUpdate();
            } catch (SQLException exception) {
                connection.rollback();

                logger.error(exception);
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Account get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getAccountMapper().map(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Account entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Account entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Account entity) {
        throw new UnsupportedOperationException();
    } */
}
