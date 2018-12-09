package ua.training.model.entity;

import ua.training.model.exception.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This entity is general template for all accounts. It contains setters and getters for all fields and in addition a
 * number of methods ({@link Account#isActive()}, {@link Account#withdrawFromAccount(BigDecimal)}) the realization of
 * witch could depend on realization of this abstract class.
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
    private List<Long> holders;

    Account(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, int updatePeriod, List<Long> holders) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.expiresEnd = expiresEnd;
        this.updatePeriod = updatePeriod;
        this.holders = holders;
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

    public List<Long> getHolders() {
        return holders;
    }

    public void setHolders(List<Long> holders) {
        this.holders = holders;
    }

    /**
     * This method tell if the account is not the end of expires.
     * @return {@code true} if account is active, else return {@code false}
     */
    public boolean isActive() {
        return LocalDate.now().isBefore(expiresEnd);
    }

    /**
     * This method is common for all account types. The method put money into this account.
     * @param amount The funds amount that must be putted into account (positive number).
     * @return Balance of the account after transaction
     */
    public BigDecimal refillAccount(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;
    }

    /**
     * This abstract method must be realized in all subclasses and the realization must depends on account policy. The
     * method withdraw money from account if it is enough for, else throws an exception.
     * @param amount The amount of money that must be withdraw from account (positive number)
     * @return Balance of the account after transaction
     * @throws NotEnoughMoneyException The exception is thrown if and only if on the account is not enough money for
     * transaction due to account policy
     */
    public abstract BigDecimal withdrawFromAccount(BigDecimal amount) throws NotEnoughMoneyException;
}
