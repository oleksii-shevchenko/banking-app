package ua.training.model.dao;

import ua.training.model.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public interface AccountDao extends Dao<Long, Account> {
    List<Long> getAllUserAccountsIds(Long userId);
    List<Account> getAllUserAccounts(Long userId);

    void createAccount(Long userId, Account account);
    void closeAccount(Long userId, Long accountId);

    void makeTransfer(Long senderId, Long receiverId, BigDecimal amount);
    void makeUpdate(Long accountId, Consumer<Account> updater);
}
