package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Request;

import java.sql.ResultSet;

public class JdbcRequestMapper implements Mapper<Request, ResultSet> {
    @Override
    public Request map(ResultSet resultSet) {
        return null;
    }
}
