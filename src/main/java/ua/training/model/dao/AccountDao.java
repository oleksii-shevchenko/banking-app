package ua.training.model.dao;

import ua.training.model.entity.Account;

import java.util.List;


/**
 * This is extension of template dao for {@link Account} entity
 * @see Dao
 * @see Account
 * @author Oleksii Shevchenko
 */
public interface AccountDao extends Dao<Long, Account> {
    List<Account> getActiveAccounts();

    List<Account> getUserAccounts(Long userId);

    long openAccount(Long userId, Account account);
    void blockAccount(Long accountId);
    void closeAccount(Long accountId);
}
