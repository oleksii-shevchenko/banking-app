package ua.training.model.exception;

/**
 * This exception is thrown when there is no user in storage with specified login.
 * @author Oleksii Shevchenko
 * @see ua.training.model.dao.jdbc.JdbcUserDao
 */
public class NoSuchUserException extends Exception {
}
