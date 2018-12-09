package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Permission;
import ua.training.model.entity.User;
import ua.training.model.exception.AliveAccountException;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static Logger logger = LogManager.getLogger(JdbcUserDao.class);

    @Override
    public User getUserByLogin(String login) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.get.by.login"))) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getUserMapper().map(resultSet);
            } else {
                throw new NoSuchUserException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Long> getAccountHoldersIds(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.id.by.account"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Long> holdersIds = new ArrayList<>();
            while (resultSet.next()) {
                holdersIds.add(resultSet.getLong("holder_id"));
            }

            return holdersIds;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<User> getAccountHolders(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.user.by.account"))) {
            preparedStatement.setLong(1, accountId);

            Mapper<User, ResultSet> mapper = new JdbcMapperFactory().getUserMapper();
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

    public Permission getPermissions(Long holderId, Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement getPermissionStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.permission"))) {
            getPermissionStatement.setLong(1, holderId);
            getPermissionStatement.setLong(2, accountId);

            ResultSet resultSet = getPermissionStatement.executeQuery();

            if (resultSet.next()) {
                return Permission.valueOf(resultSet.getString("permission"));
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void removeAccountHolder(Long holderId, Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.remove"))) {
            preparedStatement.setLong(1, holderId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    @Override
    public void addAccountHolder(Long holderId, Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.insert"))) {
            preparedStatement.setLong(1, holderId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.setString(3, Permission.RESTRICTED.name());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    @Override
    public User get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getUserMapper().map(resultSet);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException | RuntimeException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    //todo add triggers to db: 45001 not unique login, 45002 not unique email
    @Override
    public Long insert(User entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.insert"), Statement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public void update(User entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.update"))) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
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

    @Override
    public void remove(User entity) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            try (PreparedStatement removeUserStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.remove"));
                 PreparedStatement countAccountsStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.count.account.by.user"))) {
                countAccountsStatement.setLong(1, entity.getId());

                ResultSet resultSet = countAccountsStatement.executeQuery();

                long userAccountsNumber;
                if (resultSet.next()) {
                    userAccountsNumber = resultSet.getLong(1);
                } else {
                    throw new SQLException();
                }

                if (userAccountsNumber == 0) {
                    removeUserStatement.setLong(1, entity.getId());
                    removeUserStatement.executeUpdate();
                } else {
                    throw new AliveAccountException();
                }

                connection.commit();
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
