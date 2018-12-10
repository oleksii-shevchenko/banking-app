package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link CreditAccount}. It if used as sub-mapper in
 * {@link JdbcAccountMapper}.
 * @see Mapper
 * @see CreditAccount
 * @see JdbcAccountMapper
 * @author Oleksii Shevchenko
 */
public class JdbcCreditAccountMapper implements Mapper<Account> {
    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        return CreditAccount.getBuilder()
                .setId(resultSet.getLong("account_id"))
                .setBalance(resultSet.getBigDecimal("balance"))
                .setCurrency(Currency.valueOf(resultSet.getString("account_currency")))
                .setExpiresEnd(resultSet.getDate("expires_end").toLocalDate())
                .setUpdatePeriod(resultSet.getInt("update_period"))
                .setStatus(Account.Status.valueOf(resultSet.getString("account_status")))
                .setCreditLimit(resultSet.getBigDecimal("credit_limit"))
                .setCreditRate(resultSet.getBigDecimal("credit_rate"))
                .build();
    }
}
