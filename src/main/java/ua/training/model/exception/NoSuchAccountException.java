package ua.training.model.exception;

public class NoSuchAccountException extends RuntimeException{
    private Long accountId;

    public NoSuchAccountException(Long accountId) {
        this.accountId = accountId;
    }

    public NoSuchAccountException(String message, Long accountId) {
        super(message);
        this.accountId = accountId;
    }
}
