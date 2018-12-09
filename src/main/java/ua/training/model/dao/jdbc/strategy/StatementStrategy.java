package ua.training.model.dao.jdbc.strategy;

import ua.training.model.entity.Account;

import java.sql.PreparedStatement;

public interface StatementStrategy {
    void setStatementParameters(Account account, PreparedStatement preparedStatement);
}
