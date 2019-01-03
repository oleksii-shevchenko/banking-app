package ua.training.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic interface for mappers from result set to entities.
 * @see ResultSet
 * @param <E> Targeted entity
 * @author Oleksii Shevchenko
 */
public interface Mapper<E> {
    E map(ResultSet resultSet) throws SQLException;
}
