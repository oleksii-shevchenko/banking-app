package ua.training.model.dao.jdbc;

import java.util.ResourceBundle;


/**
 * This class provides sql queries for users, created using caching principle.
 * @author Oleksii Shevchenko
 */
public class QueriesManager {
    private static ResourceBundle queries = ResourceBundle.getBundle("query");

    public static String getQuery(String key) {
        return queries.getString(key);
    }
}
