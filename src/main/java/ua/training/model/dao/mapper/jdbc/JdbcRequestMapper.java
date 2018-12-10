package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Request;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRequestMapper implements Mapper<Request, ResultSet> {
    @Override
    public Request map(ResultSet resultSet) {
        try {
            return Request.getBuilder()
                    .setId(resultSet.getLong("request_id"))
                    .setRequesterId(resultSet.getLong("requester_id"))
                    .setType(Request.Type.valueOf(resultSet.getString("type")))
                    .setCurrency(Currency.valueOf(resultSet.getString("currency")))
                    .setCompleted(resultSet.getBoolean("is_completed"))
                    .build();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
