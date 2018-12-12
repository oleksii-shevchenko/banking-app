package ua.training.model.service.schedules;

import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.service.ScheduledUpdateService;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Realization of credit account updater. It modifies account according to credit policy.
 * @see ScheduledUpdateService
 * @author Oleksii Shevchenko
 */
public class CreditUpdater implements Function<Account, Transaction> {
    /**
     * Method realize credit policy. Account must be credit, if it isn't, then throws exception. If account has non
     * negative balance then do no update and throws .
     * If account has negative account then accrues interest on the loan and creates transaction based on update.
     * @param account Targeted credit account.
     * @return Transaction based on update.
     */
    @Override
    public Transaction apply(Account account) {
        CreditAccount creditAccount = (CreditAccount) account;

        if (!hasNegativeBalance(creditAccount)) {

        }
        BigDecimal loan = computeLoan(creditAccount);
        Transaction transaction = Transaction.getBuilder()
                .setReceiver(creditAccount.getId())
                .setType(Transaction.Type.REGULAR)
                .setAmount(loan)
                .setCurrency(creditAccount.getCurrency())
                .build();
        creditAccount.setBalance(creditAccount.getBalance().add(loan));
        return transaction;
    }

    private boolean hasNegativeBalance(CreditAccount creditAccount) {
        return creditAccount.getBalance().compareTo(BigDecimal.ZERO) < 0;
    }

    private BigDecimal computeLoan(CreditAccount creditAccount) {
        return creditAccount.getBalance().multiply(creditAccount.getCreditRate());
    }
}
