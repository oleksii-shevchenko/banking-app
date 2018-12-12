package ua.training.model.dao;

import ua.training.model.entity.Permission;
import ua.training.model.entity.User;

import java.util.List;

/**
 * This is extension of template dao for {@link User} entity.
 * @see Dao
 * @see User
 * @author Oleksii Shevchenko
 */
public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login);

    List<User> getAccountHolders(Long accountId);

    Permission getPermissions(Long holderId, Long accountId);

    void removeAccountHolder(Long holderId, Long accountId);
    void addAccountHolder(Long holderId, Long accountId);
}
