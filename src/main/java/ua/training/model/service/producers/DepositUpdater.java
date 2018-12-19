package ua.training.model.service.producers;

import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;

import java.math.BigDecimal;
import java.util.Optional;

public class DepositUpdater implements TransactionProducer{
    @Override
    public Optional<Transaction> produce(Account account) throws CancelingTaskException {
        if (account.isNonActive()) {
            throw new CancelingTaskException();
        }

        if (account.getBalance().equals(BigDecimal.ZERO)) {
            return Optional.empty();
        }

        DepositAccount depositAccount = (DepositAccount) account;

        Transaction transaction = Transaction.getBuilder()
                .setSender(depositAccount.getId())
                .setReceiver(depositAccount.getId())
                .setType(Transaction.Type.REGULAR)
                .setAmount(depositAccount.getBalance().multiply(depositAccount.getDepositRate()))
                .setCurrency(depositAccount.getCurrency())
                .build();

        depositAccount.setBalance(depositAccount.getBalance().add(transaction.getAmount()));

        return Optional.of(transaction);
    }
}
