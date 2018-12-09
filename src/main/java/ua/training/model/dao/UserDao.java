package ua.training.model.dao;

import ua.training.model.entity.Permission;
import ua.training.model.entity.User;

import java.util.List;

public interface UserDao extends Dao<Long, User> {
    User getUserByLogin(String login);

    List<Long> getAccountHoldersIds(Long accountId);
    List<User> getAccountHolders(Long accountId);

    Permission getPermissions(Long holderId, Long accountId);
    void removeAccountHolder(Long holderId, Long accountId);
    void addAccountHolder(Long holderId, Long accountId);
}
