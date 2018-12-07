package ua.training.model.dao.jdbc;

import ua.training.model.dao.UserDao;
import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public class JdbcUserDao implements UserDao {
    public JdbcUserDao() {
        super();
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public List<Long> getAllUserAccountIds(Long userId) {
        return null;
    }

    @Override
    public List<Account> getAllUserAccounts(Long userId) {
        return null;
    }

    @Override
    public User get(Long key) {
        return null;
    }

    @Override
    public List<User> get(List<Long> keys) {
        return null;
    }

    @Override
    public void insert(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void remove(User entity) {

    }
}
