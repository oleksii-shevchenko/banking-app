package ua.training.model.dao;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public interface TransactionDao extends Dao<Long, Transaction> {
    List<Transaction> getAccountTransactions(Long accountId);

    void makeTransfer(Long senderId, Long receiverId, BigDecimal amount);
    void makeUpdate(Long accountId, Consumer<Account> updater);
    void makeRefill(Long accountId, BigDecimal amount);
}
