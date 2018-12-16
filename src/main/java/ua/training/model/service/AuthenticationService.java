package ua.training.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.WrongPasswordException;

public class AuthenticationService {
    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authenticateUser(String login, String password) throws NoSuchUserException, WrongPasswordException {
        User user = userDao.getUserByLogin(login);
        if (DigestUtils.sha256Hex(password).equals(user.getPasswordHash())) {
            return user;
        } else {
            throw new WrongPasswordException();
        }
    }

    public void registerUser(User user) {

    }
}
