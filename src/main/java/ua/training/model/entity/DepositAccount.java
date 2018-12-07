package ua.training.model.entity;

import ua.training.model.exception.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * This realization of abstract class {@link Account} that implements Deposit Policy. That means that the deposit must
 * be accrued on the account every update period due to deposit rate.
 * @author Oleksii Shevhenko
 * @see ua.training.model.entity.Account
 */
public class DepositAccount extends Account {
    private BigDecimal depositRate;

    private DepositAccount(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, int updatePeriod,
                          boolean corporate, List<Long> holders, BigDecimal depositRate) {
        super(id, balance, currency, expiresEnd, updatePeriod, corporate, holders);
        this.depositRate = depositRate;
    }

    public BigDecimal getDepositRate() {
        return depositRate;
    }

    public DepositAccount setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
        return this;
    }

    /**
     * The realization of abstract method {@link Account#withdrawFromAccount(BigDecimal)} due to deposit policy
     * @param amount The amount of money that must be withdraw from account (positive number)
     * @return Balance of the account after transaction
     * @throws NotEnoughMoneyException The exception is thrown if and only is the
     * expression 'balance - amount' is negative
     */
    @Override
    public BigDecimal withdrawFromAccount(BigDecimal amount) throws NotEnoughMoneyException {
        BigDecimal balanceAfterTransaction = this.getBalance().subtract(amount);
        if (balanceAfterTransaction.compareTo(BigDecimal.ZERO) >= 0) {
            this.setBalance(balanceAfterTransaction);
            return balanceAfterTransaction;
        } else {
            //Todo add message and  logger
            throw new NotEnoughMoneyException(this);
        }
    }

    /**
     * This class realize pattern Builder for class {@link DepositAccount}
     * @author Oleksii Shevchenko
     * @see DepositAccount
     */
    public static class DepositAccountBuilder {
        private long id;
        private BigDecimal balance;
        private Currency currency;
        private LocalDate expiresEnd;
        private int updatePeriod;
        private boolean corporate;
        private List<Long> holders;
        private BigDecimal depositRate;

        public DepositAccountBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DepositAccountBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public DepositAccountBuilder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public DepositAccountBuilder setExpiresEnd(LocalDate expiresEnd) {
            this.expiresEnd = expiresEnd;
            return this;
        }

        public DepositAccountBuilder setUpdatePeriod(int updatePeriod) {
            this.updatePeriod = updatePeriod;
            return this;
        }

        public DepositAccountBuilder setCorporate(boolean corporate) {
            this.corporate = corporate;
            return this;
        }

        public DepositAccountBuilder setHolders(List<Long> holders) {
            this.holders = holders;
            return this;
        }

        public DepositAccountBuilder setDepositRate(BigDecimal depositRate) {
            this.depositRate = depositRate;
            return this;
        }

        public DepositAccount build() {
            return new DepositAccount(id, balance, currency, expiresEnd, updatePeriod, corporate, holders, depositRate);
        }
    }

    /**
     * The method for obtaining builder for class {@link DepositAccount}
     * @return The DepositAccountBuilder instance, that are builder for class {@link DepositAccount}
     * @see DepositAccount
     * @see DepositAccountBuilder
     */
    public static DepositAccountBuilder getBuilder() {
        return new DepositAccountBuilder();
    }
}
