package ua.training.model.entity;


/**
 * This entity represents the requests that user can make to administrators. There is fix number of requests that user
 * can make. The class has POJO structure.
 * @author Oleksii Shevchenko
 * @see User
 */
public class Request {
    private long id;
    private long requesterId;
    private Type type;
    private boolean completed;

    /**
     * The enum of possible user requests.
     * @author Oleksii Shevhcenko
     */
    public enum Type {
        CREATE_ORDINARY_DEPOSIT_ACC, CREATE_CORPORATE_DEPOSIT_ACC, CREATE_ORDINARY_CREDIT_ACC, CREATE_CORPORATE_CREDIT_ACC
    }

    private Request(long id, long requesterId, Type type, boolean completed) {
        this.id = id;
        this.requesterId = requesterId;
        this.type = type;
        this.completed = completed;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
        private boolean completed;

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

        public RequestBuilder setCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Request build() {
            return new Request(id, requesterId, type, completed);
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
