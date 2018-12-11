package ua.training.model.service;

import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.TrivialUpdateException;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Realization of deposit account updater. It modifies account according to deposit policy.
 * @see ScheduledUpdateService
 * @author Oleksii Shevchenko
 */
public class DepositUpdater implements Function<Account, Transaction> {
    /**
     * Method realize deposit policy. Account must be deposit, if it isn't, then throws exception. If account has
     * zero balance then do no update and throws {@link TrivialUpdateException}). If account has positive balance
     * then accrues interest on the deposit and creates transaction based on update.
     * @see TrivialUpdateException
     * @see ua.training.model.dao.TransactionDao#makeUpdate(Long, Function)
     * @param account Targeted deposit account.
     * @return Transaction based on update.
     */
    @Override
    public Transaction apply(Account account) {
        DepositAccount depositAccount = (DepositAccount) account;

        if (depositAccount.getBalance().compareTo(BigDecimal.ZERO) == 0) {
            throw new TrivialUpdateException();
        }
        BigDecimal interest = computeInterest(depositAccount);
        Transaction transaction = Transaction.getBuilder()
                .setReceiver(depositAccount.getId())
                .setType(Transaction.Type.REGULAR)
                .setAmount(interest)
                .setCurrency(depositAccount.getCurrency())
                .build();
        depositAccount.setBalance(depositAccount.getBalance().add(interest));
        return transaction;
    }

    private BigDecimal computeInterest(DepositAccount depositAccount) {
        return depositAccount.getBalance().multiply(depositAccount.getDepositRate());
    }
}
