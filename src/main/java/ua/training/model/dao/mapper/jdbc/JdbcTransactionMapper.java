package ua.training.model.dao.mapper.jdbc;

import org.springframework.stereotype.Component;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link Transaction}
 * @see Transaction
 * @see Mapper
 * @author Oleksii Shevchenko
 */
@Component
public class JdbcTransactionMapper implements Mapper<Transaction> {
    @Override
    public Transaction map(ResultSet resultSet) throws SQLException {
        return Transaction.getBuilder()
                .setId(resultSet.getLong("transaction_id"))
                .setTime(resultSet.getTimestamp("transaction_time").toLocalDateTime())
                .setSender(resultSet.getLong("sender"))
                .setReceiver(resultSet.getLong("receiver"))
                .setType(Transaction.Type.valueOf(resultSet.getString("transaction_type")))
                .setAmount(resultSet.getBigDecimal("transaction_amount"))
                .setCurrency(Currency.valueOf(resultSet.getString("transaction_currency")))
                .build();
    }
}
