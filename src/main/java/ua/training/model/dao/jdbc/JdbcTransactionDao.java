package ua.training.model.dao.jdbc;

import ua.training.model.dao.TransactionDao;
import ua.training.model.entity.Transaction;

import java.util.List;

public class JdbcTransactionDao implements TransactionDao {
    @Override
    public List<Transaction> getAllTransactionsForAccount(Long accountId) {
        return null;
    }

    @Override
    public Transaction get(Long key) {
        return null;
    }

    @Override
    public List<Transaction> get(List<Long> keys) {
        return null;
    }

    @Override
    public Long insert(Transaction entity) {
        return null;
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void remove(Transaction entity) {

    }
}
