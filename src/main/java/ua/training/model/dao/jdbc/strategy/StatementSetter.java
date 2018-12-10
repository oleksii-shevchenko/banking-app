package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This service provides interface for account-type-specific mapping entities of {@link Account} subclasses to
 * prepared statements.
 * @author Oleksii Shevchenko
 */
public interface StatementSetter {
    void setStatementParameters(Account account, PreparedStatement preparedStatement) throws SQLException;
}
