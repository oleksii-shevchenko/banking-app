package ua.training.model.dao;

import ua.training.model.entity.Account;

import java.util.List;

public interface AccountDao extends Dao<Long, Account> {
    List<Long> getUserAccountsIds(Long userId);
    List<Account> getUserAccounts(Long userId);

    long createAccount(Long userId, Account account);
    void blockAccount(Long accountId);
    void closeAccount(Long accountId);
}
