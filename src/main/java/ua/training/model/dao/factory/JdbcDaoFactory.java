package ua.training.model.dao.factory;

import ua.training.model.dao.*;
import ua.training.model.dao.jdbc.*;

/**
 * Implementation of abstract dao factory for jdbc dao implementations.
 * @see DaoFactory
 * @author Oleksii Shevchenko
 */
public class JdbcDaoFactory implements DaoFactory{
    private static JdbcDaoFactory factory;

    static {
        factory = new JdbcDaoFactory();
    }

    /**
     * This method used for getting cached jdbc dao factory. Note that creating this factory using constructor is
     * allowed, but not recommended for saving memory.
     * @return Cached JdbcDaoFactory
     */
    public static DaoFactory getInstance() {
        return factory;
    }

    @Override
    public AccountDao getAccountDao() {
        return new JdbcAccountDao(ConnectionsPool.getDataSource());
    }

    @Override
    public InvoiceDao getInvoiceDao() {
        return new JdbcInvoiceDao(ConnectionsPool.getDataSource());
    }

    @Override
    public RequestDao getRequestDao() {
        return new JdbcRequestDao(ConnectionsPool.getDataSource());
    }

    @Override
    public TransactionDao getTransactionDao() {
        return new JdbcTransactionDao(ConnectionsPool.getDataSource());
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(ConnectionsPool.getDataSource());
    }
}
