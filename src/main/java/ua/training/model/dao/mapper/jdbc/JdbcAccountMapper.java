package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JdbcAccountMapper implements Mapper<Account> {
    private static Map<String, Mapper<Account>> subMappers;

    static {
        subMappers = new HashMap<>();
        subMappers.put("CreditAccount", new JdbcCreditAccountMapper());
        subMappers.put("DepositAccount", new JdbcDepositAccountMapper());
    }

    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        return subMappers.get(resultSet.getString("account_type")).map(resultSet);
    }
}
