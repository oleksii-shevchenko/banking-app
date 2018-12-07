package ua.training.model.dao.factory;

import ua.training.model.dao.*;
import ua.training.model.dao.jdbc.*;

public class JdbcDaoFactory implements DaoFactory{
    @Override
    public AccountDao getAccountDao() {
        return new JdbcAccountDao();
    }

    @Override
    public PaymentDao getPaymentDao() {
        return new JdbcPaymentDao();
    }

    @Override
    public RequestDao getRequestDao() {
        return new JdbcRequestDao();
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
