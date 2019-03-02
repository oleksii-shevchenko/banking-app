package ua.training.model.dao.mapper.factory;

import org.springframework.stereotype.Component;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.jdbc.*;
import ua.training.model.entity.*;

/**
 * Realization abstract mappers factory for jdbc mappers. The creating dependencies by using operator new is used only
 * for legacy after add spring di.
 * @see Mapper
 * @see MapperFactory
 * @author Oleksii Shevchenko
 */
@Component("jdbcMapperFactory")
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
