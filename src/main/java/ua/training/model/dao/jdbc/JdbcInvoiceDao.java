package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.InvoiceDao;
import ua.training.model.entity.Invoice;

import java.util.List;

public class JdbcInvoiceDao implements InvoiceDao {
    private static Logger logger = LogManager.getLogger(JdbcInvoiceDao.class);

    @Override
    public List<Invoice> getInvoicesByRequester(Long accountId) {
        return null;
    }

    @Override
    public List<Invoice> getInvoicesByPayer(Long accountId) {
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
    public List<Invoice> getAllPaymentsByRequester(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.requester"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Invoice, ResultSet> mapper = new JdbcMapperFactory().getPaymentMapper();

            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoices.add(mapper.map(resultSet));
            }

            return invoices;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Invoice> getAllPaymentsByPayer(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.payer"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Invoice, ResultSet> mapper = new JdbcMapperFactory().getPaymentMapper();

            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoices.add(mapper.map(resultSet));
            }

            return invoices;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Invoice get(Long key) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.id"))) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new JdbcMapperFactory().getPaymentMapper().map(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Invoice entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.insert"), Statement.RETURN_GENERATED_KEYS)) {
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
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(Invoice entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.update"))) {
            setStatementParameters(entity, preparedStatement);
            preparedStatement.setLong(8, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void remove(Invoice entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.remove"))) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    private void setStatementParameters(Invoice entity, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, entity.getRequester());
        preparedStatement.setLong(2, entity.getPayer());
        preparedStatement.setBigDecimal(3, entity.getAmount());
        preparedStatement.setString(4, entity.getCurrency().name());
        preparedStatement.setString(5, entity.getStatus().name());
        preparedStatement.setString(6, entity.getDescription());
        preparedStatement.setLong(7, entity.getTransaction());
    } */
}
