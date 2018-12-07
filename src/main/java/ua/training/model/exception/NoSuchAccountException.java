package ua.training.model.exception;

public class NoSuchAccountException extends Exception{
    private Long accountId;

    public NoSuchAccountException(Long accountId) {
        this.accountId = accountId;
    }

    public NoSuchAccountException(String message, Long accountId) {
        super(message);
        this.accountId = accountId;
    }
}
