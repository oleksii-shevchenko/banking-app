package ua.training.model.dao.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * This class provides connections to database using pool structure. Access provided via singleton pattern.
 * @author Oleksii Shevchenko
 */
public final class ConnectionsPool {
    private static Logger logger = LogManager.getLogger(ConnectionsPool.class);
    private static DataSource dataSource;

    private ConnectionsPool() {}

    static {
        ResourceBundle config = ResourceBundle.getBundle("database");
        BasicDataSource poolingSource = new BasicDataSource();
        poolingSource.setDriverClassName(config.getString("db.connection.driver"));
        poolingSource.setUrl(config.getString("db.connection.url"));
        poolingSource.setUsername(config.getString("db.connection.user"));
        poolingSource.setPassword(config.getString("db.connection.pass"));
        poolingSource.setMaxIdle(Integer.parseInt(config.getString("db.connection.idle.max")));
        poolingSource.setMinIdle(Integer.parseInt(config.getString("db.connection.idle.min")));
        dataSource = poolingSource;
    }

    /**
     * Method return {@link java.sql.Connection} from pool using principle "one stop shopping"
     * @return Connection to database
     */
    @Deprecated
    static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Method return {@code DataSource} instance according to singleton pattern. The data source works as a connections
     * pool using principle "one stop shopping".
     * @return Data source for getting connections to database.
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}
