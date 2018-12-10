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
    /**
     * This method returns user by login. If there is no such user, then throws
     * {@link ua.training.model.exception.NoSuchUserException}
     */
    User getUserByLogin(String login);

    /**
     * This method returns holders ids for specified account.
     * @param accountId Targeted account.
     * @return List of account holders ids.
     */
    List<Long> getAccountHoldersIds(Long accountId);

    /**
     * This method returns holders for specified account.
     * @param accountId Targeted account.
     * @return List of account holders.
     */
    List<User> getAccountHolders(Long accountId);

    Permission getPermissions(Long holderId, Long accountId);

    /**
     * Method removes account holder, if it is not account owner (permissions - ALL).
     * @param holderId Targeted holder
     * @param accountId Targeted account
     * @see AccountDao#closeAccount(Long)
     */
    void removeAccountHolder(Long holderId, Long accountId);
    void addAccountHolder(Long holderId, Long accountId);
}
