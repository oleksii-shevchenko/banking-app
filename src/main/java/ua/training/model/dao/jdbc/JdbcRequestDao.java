package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.RequestDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.dto.PageDto;
import ua.training.model.entity.Request;
import ua.training.model.exception.UnsupportedOperationException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization of {@link RequestDao} for database source using jdbc library.
 * @see RequestDao
 * @see ua.training.model.dao.Dao
 * @see Request
 * @author Oleksii Shevchenko
 */
public class JdbcRequestDao implements RequestDao {
    private static Logger logger = LogManager.getLogger(JdbcRequestDao.class);

    private DataSource dataSource;

    public JdbcRequestDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * This method returns entity of {@link Request} and sets request status completed. If the request is already
     * completed throws exception.
     * @param requestId Targeted request.
     */
    @Override
    public void considerRequest(Long requestId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement setCompletedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.update.considered"))) {
                setCompletedStatement.setBoolean(1, true);
                setCompletedStatement.setLong(2, requestId);
                setCompletedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException();
        }
    }

    @Override
    public PageDto<Request> getPage(int itemsNumber, int page) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            try (PreparedStatement countRequests = connection.prepareStatement(QueriesManager.getQuery("sql.requests.count"));
                 PreparedStatement getRequestsPage = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.page"))) {

                int requestsNumber;
                ResultSet resultSet = countRequests.executeQuery();
                if (resultSet.next()) {
                    requestsNumber = resultSet.getInt("requests_number");
                } else {
                    throw new SQLException();
                }

                int pagesNumber = requestsNumber % itemsNumber == 0 ? requestsNumber / itemsNumber : (requestsNumber / itemsNumber) + 1;

                if (page > pagesNumber) {
                    throw new SQLException();
                }

                int offset = itemsNumber * (page - 1);

                getRequestsPage.setInt(1, itemsNumber);
                getRequestsPage.setInt(2, offset);

                Mapper<Request> mapper = new JdbcMapperFactory().getRequestMapper();
                resultSet = getRequestsPage.executeQuery();

                List<Request> transactions = new ArrayList<>();
                while (resultSet.next()) {
                    transactions.add(mapper.map(resultSet));
                }

                PageDto<Request> pageDto = new PageDto<>();
                pageDto.setPagesNumber(pagesNumber);
                pageDto.setCurrentPage(page);
                pageDto.setItemsNumber(itemsNumber);
                pageDto.setItems(transactions);

                connection.commit();

                return pageDto;
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
     * Returns all request by completion status.
     * @param consideration Targeted completion.
     * @return List of requests.
     */
    @Override
    public List<Request> getByConsideration(boolean consideration) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.consideration"))) {
            preparedStatement.setBoolean(1, consideration);

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getRequestMapper().map(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Request entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.insert"), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getRequesterId());
            preparedStatement.setString(2, entity.getType().name());
            preparedStatement.setString(3, entity.getCurrency().name());
            preparedStatement.setBoolean(4, entity.isConsidered());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int update(Request entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remove(Request entity) {
        throw new UnsupportedOperationException();
    }
}
