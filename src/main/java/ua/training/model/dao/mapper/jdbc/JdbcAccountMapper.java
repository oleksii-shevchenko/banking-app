package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;

import java.sql.ResultSet;

public class JdbcAccountMapper implements Mapper<Account, ResultSet> {
    @Override
    public Account map(ResultSet resultSet) {
        return null;
    }
}
