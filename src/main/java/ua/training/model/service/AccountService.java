package ua.training.model.service;

import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;

import java.util.List;

public class AccountService {
    private DaoFactory factory;

    public AccountService(DaoFactory factory) {
        this.factory = factory;
    }

    public List<Account> getAccounts(Long userId) {
        return factory.getAccountDao().getUserAccounts(userId);
    }
}
