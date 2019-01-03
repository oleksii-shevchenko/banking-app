package ua.training.model.exception;

/**
 * Thrown when computed hash of password and stored in db is not the same when trying to authorize user.
 * @see ua.training.model.service.AuthenticationService
 * @author Oleksii Shevchenko
 */
public class WrongPasswordException extends Exception {
}
