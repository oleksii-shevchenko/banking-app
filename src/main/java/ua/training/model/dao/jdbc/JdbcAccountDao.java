package ua.training.model.dao.jdbc;

import ua.training.model.dao.AccountDao;
import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public class JdbcAccountDao implements AccountDao {
    @Override
    public List<Long> getAllAccountUserIds(Long accountId) {
        return null;
    }

    @Override
    public List<User> getAllAccountUsers(List accountId) {
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
    public void insert(Account entity) {

    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void remove(Account entity) {

    }
}
