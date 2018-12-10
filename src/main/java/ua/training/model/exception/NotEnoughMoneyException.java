package ua.training.model.exception;

import ua.training.model.entity.Account;

import java.math.BigDecimal;

/**
 * This exception is thrown when money on account is not enough for making transaction due to account policy.
 * @author Oleksii Shevchenko
 * @see Account#withdrawFromAccount(BigDecimal)
 */
public class NotEnoughMoneyException extends Exception{
    private Account account;

    public NotEnoughMoneyException(Account account) {
        super();
        this.account = account;
    }

    public NotEnoughMoneyException(Account account, String message) {
        super(message);
        this.account = account;
    }
}
