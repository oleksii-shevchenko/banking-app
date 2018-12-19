package ua.training.model.service.producers;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;

import java.util.Optional;

@FunctionalInterface
public interface TransactionProducer {
    Optional<Transaction> produce(Account account) throws CancelingTaskException;
}
