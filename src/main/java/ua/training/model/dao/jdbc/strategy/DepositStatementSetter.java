package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DepositStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) throws SQLException {
        DepositAccount depositAccount = (DepositAccount) account;

        preparedStatement.setBigDecimal(1, depositAccount.getBalance());
        preparedStatement.setString(2, depositAccount.getClass().getSimpleName());
        preparedStatement.setString(3, depositAccount.getCurrency().name());
        preparedStatement.setDate(4, Date.valueOf(depositAccount.getExpiresEnd()));
        preparedStatement.setInt(5, depositAccount.getUpdatePeriod());
        preparedStatement.setString(6, depositAccount.getStatus().name());
        preparedStatement.setNull(7, Types.DECIMAL);
        preparedStatement.setNull(8, Types.DECIMAL);
        preparedStatement.setBigDecimal(9, depositAccount.getDepositRate());
    }
}
