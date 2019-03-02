package ua.training.model.dao.factory;

import org.springframework.beans.factory.annotation.Lookup;
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
    @Override
    @Lookup("jdbcAccountDao")
    public AccountDao getAccountDao() {
        return null;
    }

    @Override
    @Lookup("jdbcInvoiceDao")
    public InvoiceDao getInvoiceDao() {
        return null;
    }

    @Override
    @Lookup("jdbcRequestDao")
    public RequestDao getRequestDao() {
        return null;
    }

    @Override
    @Lookup("jdbcTransactionDao")
    public TransactionDao getTransactionDao() {
        return null;
    }

    @Override
    @Lookup("jdbcUserDao")
    public UserDao getUserDao() {
        return null;
    }
}
