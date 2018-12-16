package ua.training.model.service;

import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

public class AuthenticationService {
    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public long authenticateUser(String login, String password) {
        return 0;
    }

    public void registerUser(User user) {

    }
}
