package ua.training.model.entity;

import ua.training.model.exception.NonActiveAccountException;
import ua.training.model.exception.NotEnoughMoneyException;
import ua.training.model.service.CurrencyExchangeService;

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
    private int updatePeriod;

    private DepositAccount(long id, BigDecimal balance, Currency currency, LocalDate expiresEnd, int updatePeriod,
                          Status status, List<Long> holders, BigDecimal depositRate) {
        super(id, balance, currency, expiresEnd, status, holders);
        this.depositRate = depositRate;
        this.updatePeriod = updatePeriod;
    }

    public int getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public BigDecimal getDepositRate() {
        return depositRate;
    }

    public DepositAccount setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
        return this;
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
        private Status status;
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

        public DepositAccountBuilder setHolders(List<Long> holders) {
            this.holders = holders;
            return this;
        }

        public DepositAccountBuilder setDepositRate(BigDecimal depositRate) {
            this.depositRate = depositRate;
            return this;
        }

        public DepositAccountBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public DepositAccount build() {
            return new DepositAccount(id, balance, currency, expiresEnd, updatePeriod, status, holders, depositRate);
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

    @Override
    public BigDecimal withdrawFromAccount(Transaction transaction) throws NonActiveAccountException, NotEnoughMoneyException {
        if (isNonActive()) {
            throw new NonActiveAccountException();
        }

        BigDecimal exchangeRate = new CurrencyExchangeService().exchangeRate(transaction.getCurrency(), getCurrency());
        BigDecimal balance = getBalance().subtract(transaction.getAmount().multiply(exchangeRate));

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException();
        } else {
            setBalance(balance);
        }

        return getBalance();
    }
}
