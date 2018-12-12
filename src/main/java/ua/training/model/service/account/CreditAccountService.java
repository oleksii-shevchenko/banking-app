package ua.training.model.service.account;

import ua.training.model.entity.Account;
import ua.training.model.entity.CreditAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;

public class CreditAccountService extends AccountService {
    /**
     * The realization of abstract method {@link AccountService#withdrawMoney(Account, Transaction)} due to credit
     * policy. Method makes money withdraw according to transaction parameters and modify account balance field.
     * @param account Targeted account
     * @param transaction Transaction according to witch must be made withdraw.
     * @throws NotEnoughMoneyException The exception is thrown if and only is the
     * expression 'balance + creditLimit - amount' is negative.
     */
    @Override
    public void withdrawMoney(Account account, Transaction transaction) throws NonActiveAccountException, NotEnoughMoneyException {
        if (isNotActive(account)) {
            throw new NonActiveAccountException();
        }

        CreditAccount creditAccount = (CreditAccount) account;

        BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(transaction.getCurrency(), account.getCurrency());
        BigDecimal balance = creditAccount.getBalance().subtract(transaction.getAmount().multiply(exchangeRate));

        if (balance.abs().compareTo(creditAccount.getCreditLimit()) > 0) {
            throw new NotEnoughMoneyException();
        } else {
            creditAccount.setBalance(balance.subtract(transaction.getAmount().multiply(exchangeRate).multiply(creditAccount.getCreditRate())));
        }
    }
}
