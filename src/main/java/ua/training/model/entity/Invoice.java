package ua.training.model.entity;

import java.math.BigDecimal;


/**
 * This entity represents user payment requests. It has setters and getters for all fields. The users can create payments
 * request and pay or deny them. If payment is completed successfully (if the case that payer decide to pay) the payment
 * transaction records to storage.
 * @author Oleksii Shevhcenko
 * @see User
 * @see Transaction
 */
public class Invoice {
    private long id;
    private long requester;
    private long payer;
    private BigDecimal amount;
    private Currency currency;
    private Status status;
    private String description;
    private long transaction;

    /**
     * The available payment states in system. Accepted - user accepted and make payment successfully, DENIED - user
     * denied request or user accepted it but the payment is not completed successfully, PROCESSING - user do not make
     * a decision about payment yet.
     */
    public enum Status {
        ACCEPTED, DENIED, PROCESSING
    }

    private Invoice(long id, long requester, long payer, BigDecimal amount, Currency currency, Status status, String description, long transaction) {
        this.id = id;
        this.requester = requester;
        this.payer = payer;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.description = description;
        this.transaction = transaction;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequester() {
        return requester;
    }

    public void setRequester(long requester) {
        this.requester = requester;
    }

    public long getPayer() {
        return payer;
    }

    public void setPayer(long payer) {
        this.payer = payer;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTransaction() {
        return transaction;
    }

    public void setTransaction(long transaction) {
        this.transaction = transaction;
    }

    /**
     * This class realize pattern Builder for class {@link Invoice}
     * @author Oleksii Shevchenko
     * @see Invoice
     */
    public static class InvoiceBuilder {
        private long id;
        private long requester;
        private long payer;
        private BigDecimal amount;
        private Currency currency;
        private Status status;
        private String description;
        private long transaction;

        public InvoiceBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public InvoiceBuilder setRequester(long requester) {
            this.requester = requester;
            return this;
        }

        public InvoiceBuilder setPayer(long payer) {
            this.payer = payer;
            return this;
        }

        public InvoiceBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public InvoiceBuilder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public InvoiceBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public InvoiceBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public InvoiceBuilder setTransaction(long transaction) {
            this.transaction = transaction;
            return this;
        }

        public Invoice build() {
            return new Invoice(id, requester, payer, amount, currency, status, description, transaction);
        }
    }

    /**
     * The method for obtaining builder for class {@link Invoice}
     * @return The InvoiceBuilder instance, that are builder for class {@link Invoice}
     * @see Invoice
     * @see InvoiceBuilder
     */
    public static InvoiceBuilder getBuilder() {
        return new InvoiceBuilder();
    }
}
