package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.DepositAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link DepositAccount}. It is used as sub-mapper in
 * {@link JdbcAccountMapper}
 * @see Mapper
 * @see DepositAccount
 * @see JdbcAccountMapper
 * @author Oleksii Shevchenko
 */
public class JdbcDepositAccountMapper implements Mapper<Account> {
    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        return DepositAccount.getBuilder()
                .setId(resultSet.getLong("account_id"))
                .setBalance(resultSet.getBigDecimal("balance"))
                .setCurrency(Currency.valueOf(resultSet.getString("account_currency")))
                .setExpiresEnd(resultSet.getDate("expires_end").toLocalDate())
                .setUpdatePeriod(resultSet.getInt("update_period"))
                .setStatus(Account.Status.valueOf(resultSet.getString("account_status")))
                .setDepositRate(resultSet.getBigDecimal("deposit_rate"))
                .build();
    }
}
