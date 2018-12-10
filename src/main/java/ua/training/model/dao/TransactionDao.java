package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.Currency;
import ua.training.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * This is extension of template dao for {@link Transaction} entity.
 * @see Dao
 * @see Transaction
 * @author Oleksii Shevchenko
 */
public interface TransactionDao extends Dao<Long, Transaction> {
    List<Transaction> getAccountTransactions(Long accountId);

    void makeTransfer(Long senderId, Long receiverId, BigDecimal amount, Currency currency);
    void makeUpdate(Long accountId, Function<Account, Transaction> updater);
    void makeRefill(Long accountId, BigDecimal amount, Currency currency);
}
