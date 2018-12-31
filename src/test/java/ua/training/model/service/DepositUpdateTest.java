package ua.training.model.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;
import ua.training.model.service.producers.DepositUpdater;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class DepositUpdateTest {
    @Test(expected = CancelingTaskException.class)
    public void givenNonActiveDepositAccountWhenProduceTransactionThenThrowException() throws CancelingTaskException{
        Account account = DepositAccount.getBuilder()
                .setStatus(Account.Status.CLOSED)
                .build();

        new DepositUpdater(Mockito.any(DaoFactory.class), Mockito.anyLong()).produce(account);
    }

    @Test(expected = CancelingTaskException.class)
    public void givenNonDepositAccountWhenProduceTransactionThenThrowException() throws CancelingTaskException {
        Account account = CreditAccount.getBuilder()
                .setStatus(Account.Status.ACTIVE)
                .build();

        new DepositUpdater(Mockito.any(DaoFactory.class), Mockito.anyLong()).produce(account);
    }

    @Test
    public void givenDepositAccountWithZeroBalanceWhenProduceTransactionThenReturnEmpty() throws Exception {
        Account account = DepositAccount.getBuilder()
                .setStatus(Account.Status.ACTIVE)
                .setBalance(BigDecimal.ZERO)
                .build();

        Optional<Transaction> transaction = new DepositUpdater(Mockito.any(DaoFactory.class), Mockito.anyLong()).produce(account);

        assertFalse(transaction.isPresent());
    }
}
