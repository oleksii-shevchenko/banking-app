package ua.training.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.Permission;
import ua.training.model.entity.Request;
import ua.training.model.entity.User;

import java.util.Map;

/**
 * This service designed to compose functions to work with user instance.
 * @author Oleksii Shevchenko
 */
@Service
public class UserService {
    private DaoFactory factory;

    @Autowired
    public UserService(@Qualifier("jdbcDaoFactory") DaoFactory factory) {
        this.factory = factory;
    }

    public User get(Long id) {
        return factory.getUserDao().get(id);
    }

    public void makeRequest(Request request) {
        factory.getRequestDao().insert(request);
    }

    public Map<User, Permission> getAccountHolders(Long accountId) {
        return factory.getUserDao().getAccountHoldersWithPermission(accountId);
    }

    public Permission getPermission(Long userId, Long accountId) {
        return factory.getUserDao().getPermissions(userId, accountId);
    }

    public void addHolder(Long userId, Long accountId) {
        factory.getUserDao().addAccountHolder(userId, accountId);
    }

    public void removeHolder(Long userId, Long accountId) {
        factory.getUserDao().removeAccountHolder(userId, accountId);
    }

    public Request getRequest(Long requestId) {
        return factory.getRequestDao().get(requestId);
    }

    public void considerRequest(Long requestId) {
        factory.getRequestDao().considerRequest(requestId);
    }

    public Long completeOpeningRequest(Long requestId, Account account) {
        return factory.getAccountDao().completeOpeningRequest(requestId, account);
    }
}