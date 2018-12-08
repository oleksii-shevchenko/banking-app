package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public interface AccountDao extends Dao<Long, Account> {
    List<Long> getAllUserAccountsIds(Long userId);
    List<Account> getAllUserAccounts(Long userId);
    void createAccount(User user, Account account);
    void closeAccount();
    void removeAccountUser(Long userId, Long accountId);
    void addAccountUser(User user, Long accountId);
    void makeTransfer(Long senderId, Long receiverId, BigDecimal amount);
    void makeUpdate(Long accountId, Consumer<Account> updater);
}
