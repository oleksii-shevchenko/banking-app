package ua.training.model.dao.jdbc;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.NonUniqueEmailException;
import ua.training.model.exception.NonUniqueLoginException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    @Override
    public User getUserByLogin(String login) throws NoSuchUserException {
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
            //Todo add logger
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Long> getAllAccountUserIds(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.id.by.account"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Long> userIds = new ArrayList<>();
            while (resultSet.next()) {
                userIds.add(resultSet.getLong("holder_id"));
            }

            return userIds;
        } catch (SQLException exception) {
            //Todo add logger
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<User> getAllAccountUsers(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.holders.get.user.by.account"))) {
            preparedStatement.setLong(1, accountId);

            Mapper<User, ResultSet> mapper = new JdbcMapperFactory().getUserMapper();
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapper.map(resultSet));
            }

            return users;
        } catch (SQLException exception) {
            // todo add logger
            throw new RuntimeException(exception);
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
                throw new NoSuchUserException();
            }
        } catch (SQLException | NoSuchUserException exception) {
            //todo add logger
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<User> get(List<Long> keys) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.get.by.id"))) {
            for (Long key : keys) {
                preparedStatement.setLong(1, key);
                preparedStatement.addBatch();
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<User, ResultSet> mapper = new JdbcMapperFactory().getUserMapper();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapper.map(resultSet));
            }

            return users;
        } catch (SQLException exception) {
            //todo add logger
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
            //todo add logger
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


    //todo add method for parsing sqlstate exception
    @Override
    public void update(User entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.update"))) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            //todo add logger
            switch (exception.getSQLState()) {
                case "45002":
                    throw new NonUniqueEmailException();
                case "45001":
                    throw new NonUniqueLoginException();
                default:
                    throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public void remove(User entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.users.remove"))) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
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
