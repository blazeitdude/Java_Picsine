package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_SQL = FIND_ALL + " WHERE user_id = ?";

    private static final String FIND_USER_BY_LOGIN_SQL = FIND_ALL + " WHERE user_login = ?";

    private static final String UPDATE_USER_SQL = new StringBuilder()
            .append("UPDATE users ")
            .append("SET user_login = ? ,")
            .append("user_pass = ? ")
            .append("WHERE user_id = ?").toString();

    private static final String SAVE_USER_SQL = new StringBuilder()
            .append("INSERT INTO users (user_login, user_pass) ")
            .append("VALUES (?, ?)").toString();

    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE user_id = ?";


    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.query(FIND_USER_BY_ID_SQL, new Object[]{id}, new UserMapper())
                .stream().findAny().orElse(null);
        return (Optional.ofNullable(user));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, new UserMapper());
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(SAVE_USER_SQL, user.getLogin(), user.getPassword());
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_USER_SQL, user.getLogin(), user.getPassword(), user.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_USER_SQL, id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = jdbcTemplate.query(FIND_USER_BY_LOGIN_SQL, new Object[]{login}, new UserMapper())
                .stream().findAny().orElse(null);
        return (Optional.ofNullable(user));
    }

    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("user_id"),
                    rs.getString("user_login"),
                    rs.getString("user_pass"));
        }
    }
}
