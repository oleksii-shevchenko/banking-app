package ua.training.model.dao.factory;

import org.springframework.stereotype.Component;
import ua.training.model.dao.*;

/**
 * Implementation of abstract dao factory for jdbc dao implementations. After version 1.1 (Spring DI implementing), such
 * injection approach used only for legacy purpose.
 * @see DaoFactory
 * @author Oleksii Shevchenko
 * @version 1.1
 */
@Component
public class JdbcDaoFactory implements DaoFactory{
    private AccountDao accountDao;
    private InvoiceDao invoiceDao;
    private RequestDao requestDao;
    private TransactionDao transactionDao;
    private UserDao userDao;


    @Override
    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Override
    public InvoiceDao getInvoiceDao() {
        return invoiceDao;
    }

    @Override
    public RequestDao getRequestDao() {
        return requestDao;
    }

    @Override
    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }
}
