package ua.training.model.dao.factory;

import ua.training.model.dao.*;

/**
 * Abstract factor interface for creating dao instances.
 * @see Dao
 * @author Oleksii Shevchenko
 */
public interface DaoFactory {
    AccountDao getAccountDao();
    InvoiceDao getInvoiceDao();
    RequestDao getRequestDao();
    TransactionDao getTransactionDao();
    UserDao getUserDao();
}
