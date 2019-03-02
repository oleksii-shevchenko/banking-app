package ua.training.model.entity;

import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.FixerExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This entity is general template for all accounts.
 * @author Oleksii Shevchenko
 * @see CreditAccount
 * @see DepositAccount
 */
public abstract class Account {
    private long id;
    private BigDecimal balance;
    private Currency currency;
    private LocalDate expiresEnd;
    private Status status;
    private List<Long> holders;

    public Account(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, Status status, List<Long> holders) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.expiresEnd = expiresEnd;
        this.status = status;
        this.holders = holders;
    }

    public enum Status {
        ACTIVE, BLOCKED, CLOSED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getExpiresEnd() {
        return expiresEnd;
    }

    public void setExpiresEnd(LocalDate expiresEnd) {
        this.expiresEnd = expiresEnd;
    }

    public Status getStatus() {
        return status;
    }

    public Account setStatus(Status status) {
        this.status = status;
        return this;
    }

    public List<Long> getHolders() {
        return holders;
    }

    public void setHolders(List<Long> holders) {
        this.holders = holders;
    }

    /**
     * Checks is account non active. If account status BLOCKED or CLOSED return false.
     * @return Is account non active
     */
    public boolean isNonActive() {
        return !status.equals(Status.ACTIVE);
    }

    /**
     * Replenish account balance based on information presented in transaction.
     * @param transaction Transaction according to witch mush be replenishment done.
     * @return Balance of account after replenishment.
     * @throws NonActiveAccountException Thrown if and only is {@code isNonActive()} return true.
     */
    public BigDecimal replenishAccount(Transaction transaction) throws NonActiveAccountException{
        if (isNonActive()) {
            throw new NonActiveAccountException();
        }

        BigDecimal exchangeRate = new FixerExchangeService().exchangeRate(transaction.getCurrency(), getCurrency());
        setBalance(getBalance().add(transaction.getAmount().multiply(exchangeRate)));

        return getBalance();
    }

    public abstract BigDecimal withdrawFromAccount(Transaction transaction) throws NonActiveAccountException, NotEnoughMoneyException;
}
