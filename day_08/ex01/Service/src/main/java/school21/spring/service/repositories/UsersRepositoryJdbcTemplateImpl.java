package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl extends JdbcTemplate implements UsersRepository {
    public DataSource template;

    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id=?;";
    private final String SQL_SAVE = "INSERT INTO users (email) VALUES(?);";
    private final String SQL_UPDATE = "UPDATE users SET email = ? WHERE id = ?;";
    private final String SQL_DELETE = "DELETE FROM users WHERE id=?;";
    private final String SQL_FIND_ALL = "SELECT * FROM users;";
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE id=?;";

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        super(dataSource);
        this.template = dataSource;
    }

    @Override
    public User findById(Long id) {
        return query(SQL_FIND_BY_ID, new UserMapper(), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return query(SQL_FIND_ALL, new UserMapper());
    }

    @Override
    public void save(User entity) {
        update(SQL_SAVE, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        update(SQL_UPDATE, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        update(SQL_DELETE, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = query(SQL_FIND_BY_EMAIL, new UserMapper(), new Object[]{email})
                .stream().findAny().orElse(null);
        return Optional.of(user);
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}
