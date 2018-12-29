package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Request;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link Request}.
 * @see Mapper
 * @see Request
 * @author Oleksii Shevchenko
 */
public class JdbcRequestMapper implements Mapper<Request> {
    @Override
    public Request map(ResultSet resultSet) throws SQLException {
        return Request.getBuilder()
                .setId(resultSet.getLong("request_id"))
                .setRequesterId(resultSet.getLong("requester_id"))
                .setType(Request.Type.valueOf(resultSet.getString("type")))
                .setCurrency(Currency.valueOf(resultSet.getString("currency")))
                .setConsidered(resultSet.getBoolean("is_considered"))
                .build();
    }
}
