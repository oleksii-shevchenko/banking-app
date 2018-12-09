package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;

import java.sql.PreparedStatement;

public class CreditStatementStrategy implements StatementStrategy {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) {

    }
}
