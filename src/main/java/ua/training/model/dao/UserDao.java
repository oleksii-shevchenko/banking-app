package ua.training.model.dao;

import ua.training.model.entity.Permission;
import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;

import java.util.List;
import java.util.Map;

/**
 * This is extension of template dao for {@link User} entity.
 * @see Dao
 * @see User
 * @author Oleksii Shevchenko
 */
public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login) throws NoSuchUserException;

    List<User> getAccountHolders(Long accountId);
    Map<User, Permission> getAccountHoldersWithPermission(Long accountId);

    Permission getPermissions(Long holderId, Long accountId);

    int removeAccountHolder(Long holderId, Long accountId);
    int addAccountHolder(Long holderId, Long accountId);
}
