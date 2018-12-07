package ua.training.model.dao;

import ua.training.model.entity.Transaction;

import java.util.List;

public interface TransactionDao extends Dao<Long, Transaction> {
    List<Transaction> getAllTransactionsForAccount(Long accountId);
}
