package ua.training.model.dao.jdbc;

import java.util.ResourceBundle;


/**
 * This class provides sql queries for users, created using caching principle.
 * @author Oleksii Shevchenko
 */
class QueriesManager {
    private static ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Returns sql query.
     * @param key Key of the query in resource bundle.
     * @return Sql query as string.
     */
    static String getQuery(String key) {
        return queries.getString(key);
    }
}
