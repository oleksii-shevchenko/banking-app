package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Payment;

import java.sql.ResultSet;

public class JdbcPaymentMapper implements Mapper<Payment, ResultSet> {
    @Override
    public Payment map(ResultSet resultSet) {
        return null;
    }
}
