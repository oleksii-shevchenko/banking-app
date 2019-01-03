package ua.training.model.exception;

/**
 * Thrown by scheduled task to cancel it from inside execution.
 * @see ua.training.model.service.ScheduledTaskService
 * @author Oleksii Shevchenko
 */
public class CancelingTaskException extends Exception {
}
