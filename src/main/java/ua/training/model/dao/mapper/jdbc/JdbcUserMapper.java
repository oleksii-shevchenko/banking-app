package ua.training.model.dao.mapper.jdbc;

import org.springframework.stereotype.Component;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link User}.
 * @see Mapper
 * @see User
 * @author Oleksii Shevchenko
 */
@Component
public class JdbcUserMapper implements Mapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return User.getBuilder()
                .setId(resultSet.getLong("user_id"))
                .setLogin(resultSet.getString("user_login"))
                .setPasswordHash(resultSet.getString("password_hash"))
                .setEmail(resultSet.getString("user_email"))
                .setRole(User.Role.valueOf(resultSet.getString("user_role")))
                .setFirstName(resultSet.getString("first_name"))
                .setSecondName(resultSet.getString("second_name"))
                .build();
    }
}
