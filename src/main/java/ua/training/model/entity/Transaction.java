package ua.training.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * This entity represents transactions that made by users and system. The class has POJO structure.
 * @author Oleksii Shevchenko
 * @see Account
 */
public class Transaction {
    private long id;
    private LocalDateTime time;
    private long sender;
    private long receiver;
    private Type type;
    private BigDecimal amount;
    private Currency currency;

    /**
     * The possible transaction types. If transaction type is MANUAL that means that it made by user and both fields
     * sender and receiver are present. IF transaction type is AUTO that means that if made by system (examples:
     * accrual of interest on a deposit or a loan) and only field receiver is present, the sender if default value.
     * @author Oleksii Shevhcenko
     */
    public enum Type {
        AUTO, MANUAL
    }

    private Transaction(long id, LocalDateTime time, long sender, long receiver, Type type, BigDecimal amount, Currency currency) {
        this.id = id;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * The method for obtaining builder for class {@link Transaction}.
     * @return The TransactionBuilder instance, that are builder for class Transaction
     * @see Transaction
     * @see TransactionBuilder
     */
    public static TransactionBuilder getBuilder() {
        return new TransactionBuilder();
    }

    /**
     * This class realize Builder patter for class {@link Transaction}.
     * @see Transaction
     * @author Oleksii Shevchenko
     */
    public static class TransactionBuilder {
        private long id;
        private LocalDateTime time;
        private long sender;
        private long receiver;
        private Type type;
        private BigDecimal amount;
        private Currency currency;

        public TransactionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public TransactionBuilder setSender(long sender) {
            this.sender = sender;
            return this;
        }

        public TransactionBuilder setReceiver(long receiver) {
            this.receiver = receiver;
            return this;
        }

        public TransactionBuilder setType(Type type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, time, sender, receiver, type, amount, currency);
        }
    }
}
