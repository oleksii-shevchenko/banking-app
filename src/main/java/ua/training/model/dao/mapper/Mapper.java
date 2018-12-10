package ua.training.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<E> {
    E map(ResultSet resultSet) throws SQLException;
}
