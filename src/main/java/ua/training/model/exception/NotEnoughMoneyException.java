package ua.training.model.exception;

import ua.training.model.entity.Account;

public class NotEnoughMoneyException extends Exception {
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
