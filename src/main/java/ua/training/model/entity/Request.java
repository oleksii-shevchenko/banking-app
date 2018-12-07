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

    /**
     * The enum of possible user requests.
     * @author Oleksii Shevhcenko
     */
    public enum Type {
        CREATE_ORDINARY_DEPOSIT_ACC, CREATE_CORPORATE_DEPOSIT_ACC, CREATE_ORDINARY_CREDIT_ACC, CREATE_CORPORATE_CREDIT_ACC
    }

    public Request() {}

    public Request(long id, long requesterId, Type type) {
        this.id = id;
        this.requesterId = requesterId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Request setId(long id) {
        this.id = id;
        return this;
    }

    public long getRequesterId() {
        return requesterId;
    }

    public Request setRequesterId(long requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Request setType(Type type) {
        this.type = type;
        return this;
    }
}
