package ua.training.model.service.account;

import ua.training.model.entity.Account;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;


public abstract class AccountService {
    public void chargeMoney(Account account, Transaction transaction) throws NonActiveAccountException {
        if (isNotActive(account)) {
            throw new NonActiveAccountException();
        }

        BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(transaction.getCurrency(), account.getCurrency());
        account.setBalance(account.getBalance().add(transaction.getAmount().multiply(exchangeRate)));
    }

    boolean isNotActive(Account account) {
        return !account.getStatus().equals(Account.Status.ACTIVE);
    }

    public abstract void withdrawMoney(Account account, Transaction transaction) throws NotEnoughMoneyException, NonActiveAccountException;
}