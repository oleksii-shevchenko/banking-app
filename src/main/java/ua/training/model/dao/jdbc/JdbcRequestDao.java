package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.RequestDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRequestDao implements RequestDao {
    private static Logger logger = LogManager.getLogger(JdbcRequestDao.class);

    @Override
    public List<Request> getAll() {
        try (Connection connection = ConnectionsPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.all"))) {
                return createListFromResultSet(preparedStatement.executeQuery());
            } catch (SQLException exception) {
                logger.error(exception);
                throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Request> getAllByCompletion(boolean completion) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.completion"))) {
            preparedStatement.setBoolean(1, completion);

            return createListFromResultSet(preparedStatement.executeQuery());
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
    public List<Request> get(List<Long> keys) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.id"))) {
                for (Long key : keys) {
                    preparedStatement.setLong(1, key);
                    preparedStatement.addBatch();
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                connection.commit();

                return createListFromResultSet(resultSet);
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
    public Long insert(Request entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.insert"), Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                //todo change exception policy (in all the same places) (maybe)
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(Request entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.update"))) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void remove(Request entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.remove"))) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    private List<Request> createListFromResultSet(ResultSet resultSet) throws SQLException {
        Mapper<Request, ResultSet> mapper = new JdbcMapperFactory().getRequestMapper();

        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(mapper.map(resultSet));
        }

        return requests;
    }

    private void setStatementParameters(Request entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, entity.getRequesterId());
        preparedStatement.setString(2, entity.getType().name());
        preparedStatement.setBoolean(3, entity.isCompleted());
    }
}
