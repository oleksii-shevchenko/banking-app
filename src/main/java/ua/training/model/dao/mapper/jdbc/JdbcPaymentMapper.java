package ua.training.model.dao.mapper.jdbc;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Invoice;

import java.sql.ResultSet;

public class JdbcPaymentMapper implements Mapper<Invoice, ResultSet> {
    @Override
    public Invoice map(ResultSet resultSet) {
        return null;
    }
}
