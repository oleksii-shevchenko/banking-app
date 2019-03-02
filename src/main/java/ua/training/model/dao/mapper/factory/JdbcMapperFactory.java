package ua.training.model.dao.mapper.factory;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ua.training.model.dao.mapper.Mapper;
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
    @Lookup("jdbcAccountMapper")
    public Mapper<Account> getAccountMapper() {
        return null;
    }

    @Override
    @Lookup("jdbcInvoiceMapper")
    public Mapper<Invoice> getInvoiceMapper() {
        return null;
    }

    @Override
    @Lookup("jdbcRequestMapper")
    public Mapper<Request> getRequestMapper() {
        return null;
    }

    @Override
    @Lookup("jdbcTransactionMapper")
    public Mapper<Transaction> getTransactionMapper() {
        return null;
    }

    @Override
    @Lookup("jdbcUserMapper")
    public Mapper<User> getUserMapper() {
        return null;
    }
}
