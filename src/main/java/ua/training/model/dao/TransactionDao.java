package ua.training.model.dao;

import ua.training.model.dto.PageDto;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;
import ua.training.model.service.producers.TransactionProducer;

import java.util.List;

/**
 * This is extension of template dao for {@link Transaction} entity.
 * @see Dao
 * @see Transaction
 * @author Oleksii Shevchenko
 */
public interface TransactionDao extends Dao<Long, Transaction> {
    List<Transaction> getAccountTransactions(Long accountId);

    long makeTransaction(Transaction transaction);
    long makeTransaction(Long accountId, TransactionProducer producer) throws CancelingTaskException;

    PageDto<Transaction> getPage(Long accountId, int itemsNumber, int page);
}
