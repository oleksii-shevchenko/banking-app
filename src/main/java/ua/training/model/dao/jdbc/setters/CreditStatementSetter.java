package ua.training.model.dao.jdbc.setters;

import org.springframework.stereotype.Component;
import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Realization of interface {@link StatementSetter} for mapping CreditAccount entities. Bean id is starts from upper
 * case letter only for legacy purpose after adding spring di.
 * @see ua.training.model.dao.jdbc.setters.StatementSetter
 * @see CreditAccount
 * @see Account
 * @author Oleksii Shevchenko
 */
@Component("CreditStatementSetter")
public class CreditStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) throws SQLException {
        CreditAccount creditAccount = (CreditAccount) account;

        preparedStatement.setBigDecimal(1, creditAccount.getBalance());
        preparedStatement.setString(2, creditAccount.getClass().getSimpleName());
        preparedStatement.setString(3, creditAccount.getCurrency().name());
        preparedStatement.setDate(4, Date.valueOf(creditAccount.getExpiresEnd()));
        preparedStatement.setString(5, creditAccount.getStatus().name());
        preparedStatement.setBigDecimal(6, creditAccount.getCreditLimit());
        preparedStatement.setBigDecimal(7, creditAccount.getCreditRate());
        preparedStatement.setNull(8, Types.DECIMAL);
        preparedStatement.setNull(9, Types.INTEGER);
    }
}
