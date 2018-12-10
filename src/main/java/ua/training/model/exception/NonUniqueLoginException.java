package ua.training.model.exception;

/**
 * This exception is thrown while trying to insert/update user using non unique login.
 * @author Oleksii Shevchenko
 * @see ua.training.model.dao.jdbc.JdbcUserDao
 */
public class NonUniqueLoginException extends RuntimeException {
}
