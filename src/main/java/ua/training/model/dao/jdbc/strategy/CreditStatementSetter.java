package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;

import java.sql.PreparedStatement;

public class CreditStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) {

    }
}
