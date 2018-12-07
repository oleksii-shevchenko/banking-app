package ua.training.model.dao;

import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;

import java.util.List;

public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login) throws NoSuchUserException;
    List<Long> getAllAccountUserIds(Long accountId);
    List<User> getAllAccountUsers(Long accountId);
}
