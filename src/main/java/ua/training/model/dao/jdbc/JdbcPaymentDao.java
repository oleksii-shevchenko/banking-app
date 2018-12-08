package ua.training.model.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.factory.JdbcMapperFactory;
import ua.training.model.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPaymentDao implements PaymentDao {
    private static Logger logger = LogManager.getLogger(JdbcPaymentDao.class);

    @Override
    public List<Payment> getAllPaymentsByRequester(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.requester"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Payment, ResultSet> mapper = new JdbcMapperFactory().getPaymentMapper();

            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()) {
                payments.add(mapper.map(resultSet));
            }

            return payments;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Payment> getAllPaymentsByPayer(Long accountId) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.payer"))) {
            preparedStatement.setLong(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<Payment, ResultSet> mapper = new JdbcMapperFactory().getPaymentMapper();

            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()) {
                payments.add(mapper.map(resultSet));
            }

            return payments;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Payment get(Long key) {
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
    public List<Payment> get(List<Long> keys) {
        try (Connection connection = ConnectionsPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.get.by.id"))) {
                for (Long key : keys) {
                    preparedStatement.setLong(1, key);
                    preparedStatement.addBatch();
                }

                Mapper<Payment, ResultSet> mapper = new JdbcMapperFactory().getPaymentMapper();
                ResultSet resultSet = preparedStatement.executeQuery();
                connection.commit();

                List<Payment> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(mapper.map(resultSet));
                }

                return users;
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Long insert(Payment entity) {
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
    public void update(Payment entity) {
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
    public void remove(Payment entity) {
        try (Connection connection = ConnectionsPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueriesManager.getQuery("sql.payments.remove"))) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    private void setStatementParameters(Payment entity, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, entity.getRequester());
        preparedStatement.setLong(2, entity.getPayer());
        preparedStatement.setBigDecimal(3, entity.getAmount());
        preparedStatement.setString(4, entity.getCurrency().name());
        preparedStatement.setString(5, entity.getStatus().name());
        preparedStatement.setString(6, entity.getDescription());
        preparedStatement.setLong(7, entity.getTransaction());
    }
}
