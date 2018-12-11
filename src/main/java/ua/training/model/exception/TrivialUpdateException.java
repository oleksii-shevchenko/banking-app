package ua.training.model.exception;

import java.util.function.Function;

/**
 * The exception is thrown in account updaters when the account is not needed to be updated (examples: balance of
 * deposit account is zero; balance of credit account is non negative.
 * @see ua.training.model.service.CreditUpdater
 * @see ua.training.model.service.DepositUpdater
 * @see ua.training.model.dao.TransactionDao#makeUpdate(Long, Function)
 * @author Oleksii Shevchenko
 */
public class TrivialUpdateException extends RuntimeException {
}
