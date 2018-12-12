package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * This is extension of template dao for {@link Transaction} entity.
 * @see Dao
 * @see Transaction
 * @author Oleksii Shevchenko
 */
public interface TransactionDao extends Dao<Long, Transaction> {
    List<Transaction> getAccountTransactions(Long accountId);

    long makeTransaction(Transaction transaction);
    long makeTransaction(Long accountId, Function<Account, Optional<Transaction>> transactionProducer);
}
