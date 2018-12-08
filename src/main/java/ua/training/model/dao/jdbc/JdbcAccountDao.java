package ua.training.model.dao.jdbc;

import ua.training.model.dao.AccountDao;
import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public class JdbcAccountDao implements AccountDao {
    @Override
    public List<Long> getAllUserAccountsIds(Long userId) {
        return null;
    }

    @Override
    public List<Account> getAllUserAccounts(Long userId) {
        return null;
    }

    @Override
    public void createAccount(User user, Account account) {

    }

    @Override
    public void closeAccount() {

    }

    @Override
    public void removeAccountUser(Long userId, Long accountId) {

    }

    @Override
    public void addAccountUser(User user, Long accountId) {

    }

    @Override
    public void makeTransfer(Long senderId, Long receiverId, BigDecimal amount) {

    }

    @Override
    public void makeUpdate(Long accountId, Consumer<Account> updater) {

    }

    @Override
    public Account get(Long key) {
        return null;
    }

    @Override
    public List<Account> get(List<Long> keys) {
        return null;
    }

    @Override
    public Long insert(Account entity) {
        return null;
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void remove(Account entity) {

    }
}
