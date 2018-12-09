package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.jdbc.*;
import ua.training.model.entity.*;

import java.sql.ResultSet;

public class JdbcMapperFactory implements MapperFactory <ResultSet> {
    @Override
    public Mapper<Account, ResultSet> getAccountMapper() {
        return new JdbcAccountMapper();
    }

    @Override
    public Mapper<Invoice, ResultSet> getPaymentMapper() {
        return new JdbcPaymentMapper();
    }

    @Override
    public Mapper<Request, ResultSet> getRequestMapper() {
        return new JdbcRequestMapper();
    }

    @Override
    public Mapper<Transaction, ResultSet> getTransactionMapper() {
        return new JdbcTransactionMapper();
    }

    @Override
    public Mapper<User, ResultSet> getUserMapper() {
        return new JdbcUserMapper();
    }
}
