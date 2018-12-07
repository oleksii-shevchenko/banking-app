package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.User;

import java.util.List;

public interface AccountDao extends Dao<Long, Account> {
    List<Long> getAllAccountUserIds(Long accountId);
    List<User> getAllAccountUsers(List accountId);
}
