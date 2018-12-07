package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Transaction;

import java.sql.ResultSet;

public class JdbcTransactionMapper implements Mapper<Transaction, ResultSet> {
    @Override
    public Transaction map(ResultSet resultSet) {
        return null;
    }
}
