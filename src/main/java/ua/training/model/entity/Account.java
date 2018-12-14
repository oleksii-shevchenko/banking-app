package ua.training.model.entity;

import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This entity is general template for all accounts. It has POJO structure.
 * @author Oleksii Shevchenko
 * @see CreditAccount
 * @see DepositAccount
 */
public abstract class Account {
    private long id;
    private BigDecimal balance;
    private Currency currency;
    private LocalDate expiresEnd;
    private int updatePeriod;
    private Status status;
    private List<Long> holders;

    public Account(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, int updatePeriod, Status status, List<Long> holders) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.expiresEnd = expiresEnd;
        this.updatePeriod = updatePeriod;
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

    public int getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updatePeriod = updatePeriod;
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

    boolean isNonActive() {
        return !status.equals(Status.ACTIVE);
    }

    public BigDecimal replenishAccount(Transaction transaction) throws NonActiveAccountException{
        if (isNonActive()) {
            throw new NonActiveAccountException();
        }

        BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(transaction.getCurrency(), getCurrency());
        setBalance(getBalance().add(transaction.getAmount().multiply(exchangeRate)));

        return getBalance();
    }

    public abstract BigDecimal withdrawFromAccount(Transaction transaction) throws NonActiveAccountException, NotEnoughMoneyException;
    public abstract Optional<Transaction> update();
}
