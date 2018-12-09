package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.RequestDao;
import ua.training.model.entity.Invoice;

import java.util.List;

public class JdbcRequestDao implements RequestDao {
    private static Logger logger = LogManager.getLogger(JdbcRequestDao.class);

    @Override
    public List<Invoice> getAll() {
        return null;
    }

    @Override
    public List<Invoice> getAllByCompletion(boolean completion) {
        return null;
    }

    @Override
    public Invoice get(Long key) {
        return null;
    }

    @Override
    public Long insert(Invoice entity) {
        return null;
    }

    @Override
    public void update(Invoice entity) {

    }

    @Override
    public void remove(Invoice entity) {

    }

    /*
    @Override
    public List<Invoice> getAll() {
        try (Connection connection = ConnectionsPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.get.all"))) {
                return createListFromResultSet(preparedStatement.executeQuery());
            } catch (SQLException exception) {
                logger.error(exception);
                throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Invoice> getAllByCompletion(boolean completion) {
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
    public Invoice get(Long key) {
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
    public Long insert(Invoice entity) {
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
    public void update(Invoice entity) {
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
    public void remove(Invoice entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.requests.remove"))) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    private List<Invoice> createListFromResultSet(ResultSet resultSet) throws SQLException {
        Mapper<Invoice, ResultSet> mapper = new JdbcMapperFactory().getRequestMapper();

        List<Invoice> invoices = new ArrayList<>();
        while (resultSet.next()) {
            invoices.add(mapper.map(resultSet));
        }

        return invoices;
    }

    private void setStatementParameters(Invoice entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, entity.getRequesterId());
        preparedStatement.setString(2, entity.getType().name());
        preparedStatement.setBoolean(3, entity.isCompleted());
    } */
}
