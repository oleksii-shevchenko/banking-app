package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.AccountDao;
import ua.training.model.dao.jdbc.strategy.CreditStatementSetter;
import ua.training.model.dao.jdbc.strategy.DepositStatementSetter;
import ua.training.model.dao.jdbc.strategy.StatementSetter;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Permission;
import ua.training.model.exception.ActiveAccountException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Realization of {@link AccountDao} for database source using jdbc library.
 * @see ua.training.model.dao.Dao
 * @see ua.training.model.dao.AccountDao
 * @see Account
 * @author Oleksii Shevhenko
 */
public class JdbcAccountDao implements AccountDao {
    private static Logger logger = LogManager.getLogger(JdbcAccountDao.class);

    private static Map<String, StatementSetter> statementSetters;

    static {
        statementSetters = new HashMap<>();
        statementSetters.put(DepositAccount.class.getSimpleName(), new DepositStatementSetter());
        statementSetters.put(CreditAccount.class.getSimpleName(), new CreditStatementSetter());
    }

    @Override
    public List<Account> getActiveAccounts() {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.active"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Account> mapper = new JdbcMapperFactory().getAccountMapper();

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

    /**
     * Method return user account stored in database.
     * @param userId Targeted user.
     * @return List of accounts.
     */
    @Override
    public List<Account> getUserAccounts(Long userId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.account.by.user"))) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Account> mapper = new JdbcMapperFactory().getAccountMapper();

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

    /**
     * Method creates specific account and registers user as it owner.
     * @param userId Account owner.
     * @param account Account to be created.
     * @return Account id.
     */
    @Override
    public long openAccount(Long userId, Account account) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            try (PreparedStatement insertAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.insert"), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertHolderStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.insert"))) {
                StatementSetter setter = statementSetters.get(account.getClass().getSimpleName());

                setter.setStatementParameters(account, insertAccountStatement);
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
                insertAccountStatement.executeUpdate();

                connection.commit();

                return accountId;
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

    /**
     * Method block account after expires end. Should be perform automatically by system mechanisms.
     * Only closing then possible for account.
     * @see AccountDao#closeAccount(Long)
     * @param accountId Targeted account
     */
    @Override
    public void blockAccount(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement getStatusStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.status.by.id"));
             PreparedStatement updateStatusStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.status"))) {
            getStatusStatement.setLong(1, accountId);

            ResultSet resultSet = getStatusStatement.executeQuery();

            Account.Status status;
            if (resultSet.next()) {
                status = Account.Status.valueOf(resultSet.getString("account_status"));
            } else {
                throw new SQLException();
            }

            if (status.equals(Account.Status.CLOSED)) {
                throw new SQLException();
            }

            updateStatusStatement.setString(1, Account.Status.BLOCKED.name());
            updateStatusStatement.setLong(2, accountId);
            updateStatusStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * If the balance of account zero, set to account status closed and remove all account holders.
     * @param accountId Targeted account.
     */
    @Override
    public void closeAccount(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            try (PreparedStatement updateStatusStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.update.status"));
                 PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
                 PreparedStatement removeAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.remove.account"))) {
                getAccountStatement.setLong(1, accountId);

                ResultSet resultSet = getAccountStatement.executeQuery();

                Account account;
                if (resultSet.next()) {
                    account = new JdbcMapperFactory().getAccountMapper().map(resultSet);
                } else {
                    throw new SQLException();
                }

                if (account.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                    throw new ActiveAccountException();
                }

                updateStatusStatement.setString(1, Account.Status.CLOSED.name());
                updateStatusStatement.setLong(2, accountId);
                updateStatusStatement.executeUpdate();

                removeAccountStatement.setLong(1, accountId);
                removeAccountStatement.executeUpdate();

                connection.commit();
            } catch (SQLException | ActiveAccountException exception) {
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
    public Account get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement getAccountStatement = connection.prepareStatement(QueriesManager.getQuery("sql.accounts.get.by.id"));
             PreparedStatement getHoldersStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.id.by.user"))) {
            getAccountStatement.setLong(1, key);

            ResultSet resultSet = getAccountStatement.executeQuery();

            Account account;
            if (resultSet.next()) {
                account =  new JdbcMapperFactory().getAccountMapper().map(resultSet);
            } else {
                throw new SQLException();
            }

            getAccountStatement.setLong(1, key);

            resultSet = getHoldersStatement.executeQuery();

            List<Long> holders = new ArrayList<>();
            while (resultSet.next()) {
                holders.add(resultSet.getLong("holder_id"));
            }
            account.setHolders(holders);

            return account;
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
    }
}
