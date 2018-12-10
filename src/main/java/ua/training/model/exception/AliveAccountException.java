package ua.training.model.exception;

/**
 * This exception is thrown while trying to close account with non-zero balance.
 * @author Oleksii Shevchenko
 * @see ua.training.model.dao.jdbc.JdbcAccountDao
 */
public class AliveAccountException extends Exception {
}
