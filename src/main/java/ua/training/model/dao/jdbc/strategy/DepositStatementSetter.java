package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;

import java.sql.PreparedStatement;

public class DepositStatementSetter implements StatementSetter {
    @Override
    public void setStatementParameters(Account account, PreparedStatement preparedStatement) {

    }
}
