package ua.training.model.exception;

/**
 * Thrown when trying to make some action with account witch status is {@code BLOCKED} or {@code CLOSED}
 * @see ua.training.model.entity.Account
 * @author Oleksii Shevchenko
 */
public class NonActiveAccountException extends Exception {
}
