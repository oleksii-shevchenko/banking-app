package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.*;

public interface MapperFactory <R> {
    Mapper<Account, R> getAccountMapper();
    Mapper<Invoice, R> getInvoiceMapper();
    Mapper<Request, R> getRequestMapper();
    Mapper<Transaction, R> getTransactionMapper();
    Mapper<User, R> getUserMapper();
}
