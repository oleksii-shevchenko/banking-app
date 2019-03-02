package ua.training.model.dao.jdbc.setters;

import org.springframework.stereotype.Component;
import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Realization of interface {@link StatementSetter} for mapping DepositAccounts entities. Bean id is starts from upper
 *  * case letter only for legacy purpose after adding spring di.
 * @see ua.training.model.dao.jdbc.setters.StatementSetter
 * @see DepositAccount
 * @see Account
 * @author Oleksii Shevchenko
 */
@Component("DepositStatementSetter")
public class DepositStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) throws SQLException {
        DepositAccount depositAccount = (DepositAccount) account;

        preparedStatement.setBigDecimal(1, depositAccount.getBalance());
        preparedStatement.setString(2, depositAccount.getClass().getSimpleName());
        preparedStatement.setString(3, depositAccount.getCurrency().name());
        preparedStatement.setDate(4, Date.valueOf(depositAccount.getExpiresEnd()));
        preparedStatement.setString(5, depositAccount.getStatus().name());
        preparedStatement.setNull(6, Types.DECIMAL);
        preparedStatement.setNull(7, Types.DECIMAL);
        preparedStatement.setBigDecimal(8, depositAccount.getDepositRate());
        preparedStatement.setInt(9, depositAccount.getUpdatePeriod());
    }
}
