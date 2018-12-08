package ua.training.model.dao;

import ua.training.model.entity.User;

import java.util.List;

public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login);

    List<Long> getAllAccountUsersIds(Long accountId);
    List<User> getAllAccountUsers(Long accountId);

    void removeAccountUser(Long userId, Long accountId);
    void addAccountUser(Long userId, Long accountId);
}
