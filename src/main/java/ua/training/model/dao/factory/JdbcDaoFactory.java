package ua.training.model.dao.factory;

import ua.training.model.dao.*;
import ua.training.model.dao.jdbc.*;

public class JdbcDaoFactory implements DaoFactory{
    private static DaoFactory factory;

    static {
        factory = new JdbcDaoFactory();
    }

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
        return new JdbcTransactionDao();
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao();
    }
}
