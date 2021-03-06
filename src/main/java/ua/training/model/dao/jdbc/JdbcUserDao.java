package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.MapperFactory;
import ua.training.model.entity.Permission;
import ua.training.model.entity.User;
import ua.training.model.exception.ActiveAccountException;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This realization of {@link UserDao} for database source using jdbc library.
 * @see UserDao
 * @see User
 * @author Oleksii Shevchenko
 */
@Component
public class JdbcUserDao implements UserDao {
    private static Logger logger = LogManager.getLogger(JdbcUserDao.class);

    private DataSource dataSource;
    private MapperFactory mapperFactory;
    private QueriesManager queriesManager;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    @Qualifier("jdbcMapperFactory")
    public void setMapperFactory(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @Autowired
    public void setQueriesManager(QueriesManager queriesManager) {
        this.queriesManager = queriesManager;
    }

    /**
     * Gets entity {@link User} using unique field login. If there is no such user, then throws {@link NoSuchUserException}.
     * Note that this method do not return full user entity for improving performance (the holders are not setted), as
     * this entity used only for authentication. For getting full instances see {@link JdbcUserDao#get(Long)}.
     * @param login User login
     * @return User entity
     */
    @Override
    public User getUserByLogin(String login) throws NoSuchUserException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.users.get.by.login"))) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapperFactory.getUserMapper().map(resultSet);
            } else {
                throw new NoSuchUserException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Returns account holders list.
     * @param accountId Targeted account.
     * @return List of users.
     */
    @Override
    public List<User> getAccountHolders(Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.get.user.by.account"))) {
            preparedStatement.setLong(1, accountId);

            Mapper<User> mapper = mapperFactory.getUserMapper();
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> holders = new ArrayList<>();
            while (resultSet.next()) {
                holders.add(mapper.map(resultSet));
            }

            return holders;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Returns map holder-permission for some account.
     * @param accountId Targeted account.
     * @return Permissions of all account holders.
     */
    @Override
    public Map<User, Permission> getAccountHoldersWithPermission(Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.get.user.by.account"))) {
            preparedStatement.setLong(1, accountId);

            Mapper<User> mapper = mapperFactory.getUserMapper();
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<User, Permission> holders = new HashMap<>();
            while (resultSet.next()) {
                holders.put(mapper.map(resultSet), Permission.valueOf(resultSet.getString("permission")));
            }

            return holders;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Return user permissions for account.
     * @param holderId Targeted holder.
     * @param accountId Targeted account.
     * @return User permission.
     */
    public Permission getPermissions(Long holderId, Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getPermissionStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.get.permission"))) {
            getPermissionStatement.setLong(1, holderId);
            getPermissionStatement.setLong(2, accountId);

            ResultSet resultSet = getPermissionStatement.executeQuery();

            if (resultSet.next()) {
                return Permission.valueOf(resultSet.getString("permission"));
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }

    }

    /**
     * Removes account holder, if it is not owner (permission - ALL). Before removing checked that user is not
     * owner.
     * @param holderId Targeted holder
     * @param accountId Targeted account
     */
    @Override
    public int removeAccountHolder(Long holderId, Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement removeHolderStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.remove"));
             PreparedStatement getPermissionStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.get.permission"))) {
            getPermissionStatement.setLong(1, holderId);
            getPermissionStatement.setLong(2, accountId);

            ResultSet resultSet = getPermissionStatement.executeQuery();

            if (resultSet.next()) {
                if (Permission.valueOf(resultSet.getString("permission")).equals(Permission.ALL)) {
                    throw new SQLException();
                }
            } else {
                throw new SQLException();
            }

            removeHolderStatement.setLong(1, holderId);
            removeHolderStatement.setLong(2, accountId);

            return removeHolderStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    /**
     * Method adds holder to account with restricted permission. Only owner can add holder. Account must be active.
     * @param holderId Targeted user.
     * @param accountId Targeted account.
     */
    @Override
    public int addAccountHolder(Long holderId, Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.insert"))) {
            preparedStatement.setLong(1, holderId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.setString(3, Permission.RESTRICTED.name());

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    /**
     * This method returns fully created user instance.
     * @param key The id of user.
     * @return User instance.
     */
    @Override
    public User get(Long key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(queriesManager.getQuery("sql.users.get.full"))) {
            getUserStatement.setLong(1, key);

            ResultSet resultSet = getUserStatement.executeQuery();

            if (resultSet.next()) {
                User user =  mapperFactory.getUserMapper().map(resultSet);

                user.setAccounts(new ArrayList<>());
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    long accountId = resultSet.getLong("account_id");
                    if (accountId != 0){
                        user.getAccounts().add(resultSet.getLong("account_id"));
                    }
                }

                return user;
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Inserts User entity into DB and return generated primary key.
     * @param entity User entity that must be inserted.
     * @return Primary key of inserted user (user id).
     * @throws NonUniqueLoginException Is thrown if there is user with the same login.
     * @throws NonUniqueEmailException Is thrown if there is user with the same email.
     */
    @Override
    public Long insert(User entity) throws NonUniqueLoginException, NonUniqueEmailException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.users.insert"), Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            switch (exception.getSQLState()) {
                case "45001":
                    throw new NonUniqueLoginException();
                case "45002":
                    throw new NonUniqueEmailException();
                default:
                    throw new RuntimeException(exception);
            }
        }
    }


    /**
     * Update user record.
     * @param entity User entity that must be updated
     * @throws NonUniqueEmailException Is thrown if there is user with the same login.
     * @throws NonUniqueLoginException Is thrown if there is user with the same email.
     */
    @Override
    public int update(User entity) throws NonUniqueEmailException, NonUniqueLoginException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queriesManager.getQuery("sql.users.update"))) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.setLong(7, entity.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            switch (exception.getSQLState()) {
                case "45001":
                    throw new NonUniqueLoginException();
                case "45002":
                    throw new NonUniqueEmailException();
                default:
                    throw new RuntimeException(exception);
            }
        }
    }

    /**
     * Removes user if and only if user haven't no active accounts.
     * @param entity User that must be removed.
     */
    @Override
    public int remove(User entity) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            try (PreparedStatement removeUserStatement = connection.prepareStatement(queriesManager.getQuery("sql.users.remove"));
                 PreparedStatement countAccountsStatement = connection.prepareStatement(queriesManager.getQuery("sql.holders.count.account.by.user"))) {
                countAccountsStatement.setLong(1, entity.getId());

                ResultSet resultSet = countAccountsStatement.executeQuery();

                long userAccountsNumber;
                if (resultSet.next()) {
                    userAccountsNumber = resultSet.getLong(1);
                } else {
                    throw new SQLException();
                }

                int removed;
                if (userAccountsNumber == 0) {
                    removeUserStatement.setLong(1, entity.getId());
                    removed = removeUserStatement.executeUpdate();
                } else {
                    throw new SQLException(new ActiveAccountException());
                }

                connection.commit();

                return removed;
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

    private void setStatementParameters(User entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getLogin());
        preparedStatement.setString(2, entity.getPasswordHash());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getRole().name());
        preparedStatement.setString(5, entity.getFirstName());
        preparedStatement.setString(6, entity.getSecondName());
    }
}
