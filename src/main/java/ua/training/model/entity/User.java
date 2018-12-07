package ua.training.model.entity;

import java.util.List;


/**
 * A entity that represents app user. It has a POJO structure and builder {@link UserBuilder}, the instance of witch you
 * can obtain via static method {@link User#getBuilder()}.
 * @author Oleksii Shevchenko
 */
public class User {
    private long id;
    private String login;
    private String passwordHash;
    private String email;
    private Role role;
    private String firstName;
    private String secondName;
    private List<Long> accountIds;

    /**
     * The enum represents roles of users that are present in system.
     * @see User
     * @author Oleksii Shevchenko
     */
    public enum Role {
        GUEST, USER, ADMIN
    }

    private User(long id, String login, String passwordHash, String email, Role role, String firstName, String secondName, List<Long> accountIds) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
        this.accountIds = accountIds;
    }

    /**
     * The method for obtaining builder for class {@link User}.
     * @return The UserBuilder instance, that are builder for class User
     * @see User
     * @see UserBuilder
     */
    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    /**
     * This class realize Builder patter for class {@link User}.
     * @see User
     * @author Oleksii Shevchenko
     */
    public static class UserBuilder {
        private long id;
        private String login;
        private String passwordHash;
        private String email;
        private Role role;
        private String firstName;
        private String secondName;
        private List<Long> accountIds;

        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public UserBuilder setAccountIds(List<Long> accountIds) {
            this.accountIds = accountIds;
            return this;
        }

        public User build() {
            return new User(id, login, passwordHash, email, role, firstName, secondName, accountIds);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Long> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<Long> accountIds) {
        this.accountIds = accountIds;
    }
}
