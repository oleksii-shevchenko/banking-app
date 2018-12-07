package ua.training.model.entity;

import ua.training.model.exception.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * This realization of abstract class {@link Account} that implements Credit Policy. That means that on the account is
 * possible negative balance, but the absolute value of it must be not greater than credit limit. If the balance is
 * negative the loan interest should be accrued to the account every update period due to credit rate.
 * @author Oleksii Shevhenko
 * @see ua.training.model.entity.Account
 */
public class CreditAccount extends Account {
    private BigDecimal creditRate;
    private BigDecimal creditLimit;
    private BigDecimal creditAmount;

    private CreditAccount(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, int updatePeriod,
                         boolean corporate, List<Long> holders, BigDecimal creditRate, BigDecimal creditLimit, BigDecimal creditAmount) {
        super(id, balance, currency, expiresEnd, updatePeriod, corporate, holders);
        this.creditRate = creditRate;
        this.creditLimit = creditLimit;
        this.creditAmount = creditAmount;
    }

    public BigDecimal getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(BigDecimal creditRate) {
        this.creditRate = creditRate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * The realization of abstract method {@link Account#withdrawFromAccount(BigDecimal)} due to credit policy
     * @param amount The amount of money that must be withdraw from account (positive number)
     * @return Balance of the account after transaction
     * @throws NotEnoughMoneyException The exception is thrown if and only is the
     * expression 'balance + creditLimit - amount' is negative
     */
    @Override
    public BigDecimal withdrawFromAccount(BigDecimal amount) throws NotEnoughMoneyException {
        BigDecimal balanceAfterTransaction = this.getBalance().add(creditLimit).subtract(amount);
        if (balanceAfterTransaction.compareTo(BigDecimal.ZERO) >= 0) {
            this.setBalance(balanceAfterTransaction);
            return balanceAfterTransaction;
        } else {
            //Todo add message and logger
            throw new NotEnoughMoneyException(this);
        }
    }

    /**
     * This class realize pattern Builder for class {@link CreditAccount}
     * @author Oleksii Shevchenko
     * @see CreditAccount
     */
    public static class CreditAccountBuilder {
        private long id;
        private BigDecimal balance;
        private Currency currency;
        private LocalDate expiresEnd;
        private int updatePeriod;
        private boolean corporate;
        private List<Long> holders;
        private BigDecimal creditRate;
        private BigDecimal creditLimit;
        private BigDecimal creditAmount;

        public CreditAccountBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CreditAccountBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public CreditAccountBuilder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public CreditAccountBuilder setExpiresEnd(LocalDate expiresEnd) {
            this.expiresEnd = expiresEnd;
            return this;
        }

        public CreditAccountBuilder setUpdatePeriod(int updatePeriod) {
            this.updatePeriod = updatePeriod;
            return this;
        }

        public CreditAccountBuilder setCorporate(boolean corporate) {
            this.corporate = corporate;
            return this;
        }

        public CreditAccountBuilder setHolders(List<Long> holders) {
            this.holders = holders;
            return this;
        }

        public CreditAccountBuilder setCreditRate(BigDecimal creditRate) {
            this.creditRate = creditRate;
            return this;
        }

        public CreditAccountBuilder setCreditLimit(BigDecimal creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        public CreditAccountBuilder setCreditAmount(BigDecimal creditAmount) {
            this.creditAmount = creditAmount;
            return this;
        }

        public CreditAccount build() {
            return new CreditAccount(id, balance, currency, expiresEnd, updatePeriod, corporate, holders, creditRate, creditLimit, creditAmount);
        }
    }

    /**
     * The method for obtaining builder for class {@link CreditAccount}
     * @return The CreditAccountBuilder instance, that are builder for class {@link CreditAccount}
     * @see CreditAccount
     * @see CreditAccountBuilder
     */
    public static CreditAccountBuilder getBuilder() {
        return new CreditAccountBuilder();
    }
}
