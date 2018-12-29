package ua.training.model.entity;


/**
 * This entity represents the requests for account creation that user can make to administrators.
 * There is fix number of requests that user can make. user can choose account type and currency, other account
 * parameters are set by admin. The class has POJO structure.
 * @author Oleksii Shevchenko
 * @see User
 */
public class Request {
    private long id;
    private long requesterId;
    private Type type;
    private Currency currency;
    private boolean considered;

    /**
     * The enum of possible user requests.
     * @author Oleksii Shevhcenko
     */
    public enum Type {
        CREATE_DEPOSIT_ACCOUNT ("content.request.type.deposit"),
        CREATE_CREDIT_ACCOUNT ("content.request.type.credit");

        private String massageKey;

        Type(String massageKey) {
            this.massageKey = massageKey;
        }

        public String getMassageKey() {
            return massageKey;
        }
    }

    private Request(long id, long requesterId, Type type, Currency currency, boolean considered) {
        this.id = id;
        this.requesterId = requesterId;
        this.type = type;
        this.currency = currency;
        this.considered = considered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isConsidered() {
        return considered;
    }

    public void setConsidered(boolean considered) {
        this.considered = considered;
    }

    /**
     * This class realize Builder patter for class {@link Request}.
     * @see Request
     * @author Oleksii Shevchenko
     */
    public static class RequestBuilder {
        private long id;
        private long requesterId;
        private Type type;
        private Currency currency;
        private boolean considered;

        public RequestBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public RequestBuilder setRequesterId(long requesterId) {
            this.requesterId = requesterId;
            return this;
        }

        public RequestBuilder setType(Type type) {
            this.type = type;
            return this;
        }

        public RequestBuilder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public RequestBuilder setConsidered(boolean considered) {
            this.considered = considered;
            return this;
        }

        public Request build() {
            return new Request(id, requesterId, type, currency, considered);
        }
    }

    /**
     * The method for obtaining builder for class {@link Request}.
     * @return The RequestBuilder instance, that are builder for class Request
     * @see Request
     * @see RequestBuilder
     */
    public static RequestBuilder getBuilder() {
        return new RequestBuilder();
    }
}
