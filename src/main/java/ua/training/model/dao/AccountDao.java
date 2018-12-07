package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public interface AccountDao extends Dao<Long, Account> {
    List<Long> getAllUserAccountsIds(Long accountId);
    List<Account> getAllUserAccounts(Long accountId);
    void createAccount(User user, Account account);
    void addAccountUser(User user, Account account);
}
