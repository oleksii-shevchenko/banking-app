package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.RequestDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Request;
import ua.training.model.exception.CompletedRequestException;
import ua.training.model.exception.UnsupportedOperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRequestDao implements RequestDao {
    private static Logger logger = LogManager.getLogger(JdbcRequestDao.class);

    @Override
    public Request processRequest(Long requestId) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            try (PreparedStatement getRequestStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.id"));
                 PreparedStatement setCompletedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.request.update.completed"))) {
                getRequestStatement.setLong(1, requestId);

                ResultSet resultSet = getRequestStatement.executeQuery();

                Request request;
                if (resultSet.next()) {
                    request = new JdbcMapperFactory().getRequestMapper().map(resultSet);
                } else {
                    throw new RuntimeException();
                }

                if (request.isCompleted()) {
                    throw new CompletedRequestException();
                }

                setCompletedStatement.executeUpdate();

                return request;
            } catch (SQLException | RuntimeException exception) {
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
    public List<Request> getAllByCompletion(boolean completion) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.completion"))) {
            preparedStatement.setBoolean(1, completion);

            ResultSet resultSet  = preparedStatement.executeQuery();
            Mapper<Request> mapper = new JdbcMapperFactory().getRequestMapper();

            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(mapper.map(resultSet));
            }

            return requests;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Request get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getRequestMapper().map(resultSet);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Request entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.insert"), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getRequesterId());
            preparedStatement.setString(2, entity.getType().name());
            preparedStatement.setString(3, entity.getCurrency().name());
            preparedStatement.setBoolean(4, entity.isCompleted());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException | RuntimeException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(Request entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Request entity) {
        throw new UnsupportedOperationException();
    }
}
