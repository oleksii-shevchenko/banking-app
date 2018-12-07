package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.User;

import java.sql.ResultSet;

public class JdbcUserMapper implements Mapper<User, ResultSet> {
    @Override
    public User map(ResultSet resultSet) {
        return null;
    }
}
