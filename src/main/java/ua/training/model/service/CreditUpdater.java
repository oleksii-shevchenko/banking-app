package ua.training.model.service;

import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.TrivialUpdateException;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Realization of credit account updater. It modifies account according to credit policy. If account has non
 * negative balance then do no update ({@link CreditUpdater#apply(Account)} throws {@link TrivialUpdateException}).
 * If account has negative account then accrues interest on the loan and creates transaction based on update.
 * @see ua.training.model.dao.TransactionDao#makeUpdate(Long, Function)
 * @see TrivialUpdateException
 * @author Oleksii Shevchenko
 */
public class CreditUpdater implements Function<Account, Transaction> {
    @Override
    public Transaction apply(Account account) {
        CreditAccount creditAccount = (CreditAccount) account;

        if (!hasNegativeBalance(creditAccount)) {
            throw new TrivialUpdateException();
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
