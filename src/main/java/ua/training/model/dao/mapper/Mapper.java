package ua.training.model.dao.mapper;

import java.sql.ResultSet;

public interface Mapper<T> {
    T map(ResultSet resultSet);
}
