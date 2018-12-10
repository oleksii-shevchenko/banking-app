package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.*;

public interface MapperFactory <R> {
    Mapper<Account> getAccountMapper();
    Mapper<Invoice> getInvoiceMapper();
    Mapper<Request> getRequestMapper();
    Mapper<Transaction> getTransactionMapper();
    Mapper<User> getUserMapper();
}
