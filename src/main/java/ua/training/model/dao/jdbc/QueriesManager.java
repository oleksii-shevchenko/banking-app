package ua.training.model.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;


/**
 * This class provides sql queries for users, created using caching principle.
 * @author Oleksii Shevchenko
 */
@Component
class QueriesManager {
    private final ResourceBundle queries;

    @Autowired
    public QueriesManager(@Qualifier("queries") ResourceBundle queries) {
        this.queries = queries;
    }

    /**
     * Returns sql query.
     * @param key Key of the query in resource bundle.
     * @return Sql query as string.
     */
    String getQuery(String key) {
        return queries.getString(key);
    }
}
