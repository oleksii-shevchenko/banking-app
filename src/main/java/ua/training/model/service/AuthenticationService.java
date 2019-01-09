package ua.training.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;
import ua.training.model.exception.NoSuchUserException;
import ua.training.model.exception.WrongPasswordException;

/**
 * This service used to provide authentication mechanisms.
 * @author Oleksii Shevchenko
 */
public class AuthenticationService {
    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method used to authenticate user by login and password.
     * @param login User login.
     * @param password User password.
     * @return User instance in case of success authentication.
     * @throws NoSuchUserException Thrown if there is no user in source with such login.
     * @throws WrongPasswordException Thrown if the passwords hash is not the same.
     */
    public User authenticate(String login, String password) throws NoSuchUserException, WrongPasswordException {
        User user = userDao.getUserByLogin(login);
        if (DigestUtils.sha256Hex(password).equals(user.getPasswordHash())) {
            return user;
        } else {
            throw new WrongPasswordException();
        }
    }

    /**
     * Register user in system.
     * @param user User to register.
     */
    public Long register(User user){
        return userDao.insert(user);
    }
}
