package ua.training.model.dao.mapper.factory;

import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.Account;
import ua.training.model.entity.Invoice;
import ua.training.model.entity.Transaction;
import ua.training.model.entity.User;

public interface MapperFactory <R> {
    Mapper<Account, R> getAccountMapper();
    Mapper<Invoice, R> getPaymentMapper();
    Mapper<Invoice, R> getRequestMapper();
    Mapper<Transaction, R> getTransactionMapper();
    Mapper<User, R> getUserMapper();
}
