package ua.training.model.dao.mapper.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Realization of interface {@link Mapper} for abstract entity {@link Account}. It is used as master-mapper for all
 * Account entities. It delegates its responsibilities to sub-mappers.
 * @see Account
 * @see Mapper
 * @see JdbcCreditAccountMapper
 * @see JdbcDepositAccountMapper
 * @author Oleksii Shevchenko
 */
@Component
public class JdbcAccountMapper implements Mapper<Account> {
    private Map<String, Mapper<Account>> subMappers;

    @Autowired
    @Qualifier("jdbcSubMappers")
    public void setSubMappers(Map<String, Mapper<Account>> subMappers) {
        this.subMappers = subMappers;
    }

    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        return subMappers.get(resultSet.getString("account_type")).map(resultSet);
    }
}
