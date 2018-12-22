package ua.training.model.service.producers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.CancelingTaskException;

import java.math.BigDecimal;
import java.util.Optional;

public class DepositUpdater implements TransactionProducer, Runnable{
    private static Logger logger = LogManager.getLogger(DepositUpdater.class);

    private DaoFactory factory;
    private Long accountId;

    public DepositUpdater(DaoFactory factory, Long accountId) {
        this.factory = factory;
        this.accountId = accountId;
    }

    @Override
    public Optional<Transaction> produce(Account account) throws CancelingTaskException {
        if (account.isNonActive() || !(account instanceof DepositAccount)) {
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

    @Override
    public void run() {
        try {
            factory.getTransactionDao().makeTransaction(accountId, this::produce);
        } catch (CancelingTaskException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        } catch (RuntimeException exception) {
            logger.error(exception);
        }
    }
}
