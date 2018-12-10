package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CreditStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) throws SQLException {
        CreditAccount creditAccount = (CreditAccount) account;

        preparedStatement.setBigDecimal(1, creditAccount.getBalance());
        preparedStatement.setString(2, creditAccount.getClass().getSimpleName());
        preparedStatement.setString(3, creditAccount.getCurrency().name());
        preparedStatement.setDate(4, Date.valueOf(creditAccount.getExpiresEnd()));
        preparedStatement.setInt(5, creditAccount.getUpdatePeriod());
        preparedStatement.setString(6, creditAccount.getStatus().name());
        preparedStatement.setBigDecimal(7, creditAccount.getCreditLimit());
        preparedStatement.setBigDecimal(8, creditAccount.getCreditRate());
        preparedStatement.setNull(9, Types.DECIMAL);
    }
}
