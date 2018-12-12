package ua.training.model.service.account;

import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;
import ua.training.model.entity.Transaction;
import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.util.Optional;

public class DepositAccountService extends AccountService {
    @Override
    public void withdrawMoney(Account account, Transaction transaction) throws NonActiveAccountException, NotEnoughMoneyException {
        if (isNotActive(account)) {
            throw new NonActiveAccountException();
        }

        BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(transaction.getCurrency(), account.getCurrency());
        BigDecimal balance = account.getBalance().subtract(transaction.getAmount().multiply(exchangeRate));

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException();
        } else {
            account.setBalance(balance);
        }
    }

    public Optional<Transaction> depositUpdate(Account account) {
        DepositAccount depositAccount = (DepositAccount) account;

        if (isNotActive(depositAccount) || depositAccount.getBalance().equals(BigDecimal.ZERO)) {
            return Optional.empty();
        }

        Transaction transaction = Transaction.getBuilder()
                .setReceiver(depositAccount.getId())
                .setType(Transaction.Type.REGULAR)
                .setAmount(depositAccount.getBalance().multiply(depositAccount.getDepositRate()))
                .setCurrency(depositAccount.getCurrency())
                .build();

        depositAccount.setBalance(depositAccount.getBalance().add(transaction.getAmount()));

        return Optional.of(transaction);
    }
}
