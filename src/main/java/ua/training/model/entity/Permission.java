package ua.training.model.entity;

/**
 * This enum represents user permissions for using account. If permission is ALL that means that user is owner of this
 * account and can do everything, excluding removing himself from account holders. If permission is RESTRICTED that means
 * user can use account, but cannot remove account holders.
 * @see Account
 * @author Oleksii Shevchenko
 */
public enum Permission {
    ALL, RESTRICTED
}
