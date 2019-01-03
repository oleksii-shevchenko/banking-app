package ua.training.model.service.producers;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;

import java.util.Optional;

/**
 * Functional interface for producing transactions based on internal account state.
 * @see Account
 * @see Transaction
 * @author Oleksii Shevchenko
 */
@FunctionalInterface
public interface TransactionProducer {
    Optional<Transaction> produce(Account account) throws CancelingTaskException;
}
