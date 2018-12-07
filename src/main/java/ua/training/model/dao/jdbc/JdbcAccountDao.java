package ua.training.model.dao.jdbc;

import ua.training.model.dao.AccountDao;
import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public class JdbcAccountDao implements AccountDao {
    @Override
    public List<Long> getAllUserAccountsIds(Long accountId) {
        return null;
    }

    @Override
    public List<Account> getAllUserAccounts(Long accountId) {
        return null;
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

    @Override
    public void createAccount(User user, Account account) {

    }

    @Override
    public void addAccountUser(User user, Account account) {

    }
}
