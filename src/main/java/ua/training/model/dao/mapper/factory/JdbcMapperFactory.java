package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.jdbc.*;
import ua.training.model.entity.*;

import java.sql.ResultSet;

public class JdbcMapperFactory implements MapperFactory <ResultSet> {
    @Override
    public Mapper<Account> getAccountMapper() {
        return new JdbcAccountMapper();
    }

    @Override
    public Mapper<Invoice> getInvoiceMapper() {
        return new JdbcInvoiceMapper();
    }

    @Override
    public Mapper<Request> getRequestMapper() {
        return new JdbcRequestMapper();
    }

    @Override
    public Mapper<Transaction> getTransactionMapper() {
        return new JdbcTransactionMapper();
    }

    @Override
    public Mapper<User> getUserMapper() {
        return new JdbcUserMapper();
    }
}
