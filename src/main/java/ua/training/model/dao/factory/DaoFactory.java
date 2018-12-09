package ua.training.model.dao.factory;

import ua.training.model.dao.*;

public interface DaoFactory {
    AccountDao getAccountDao();
    InvoiceDao getPaymentDao();
    RequestDao getRequestDao();
    TransactionDao getTransactionDao();
    UserDao getUserDao();
}
