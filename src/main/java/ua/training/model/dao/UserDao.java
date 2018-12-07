package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login);
    List<Long> getAllUserAccountIds(Long userId);
    List<Account> getAllUserAccounts(Long userId);
}
