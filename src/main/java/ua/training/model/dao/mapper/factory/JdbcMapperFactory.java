package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.jdbc.*;
import ua.training.model.entity.*;

/**
 * Realization abstract mappers factory for jdbc mappers.
 * @see Mapper
 * @see MapperFactory
 * @author Oleksii Shevchenko
 */
public class JdbcMapperFactory implements MapperFactory {
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
