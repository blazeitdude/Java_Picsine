package school21.spring.service.repositories;

import com.zaxxer.hikari.HikariDataSource;
import school21.spring.service.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    HikariDataSource ds;

    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id=?;";
    private final String SQL_SAVE = "INSERT INTO users (email) VALUES(?);";
    private final String SQL_UPDATE = "UPDATE users SET email = ? WHERE id = ?;";
    private final String SQL_DELETE = "DELETE FROM users WHERE id=?;";
    private final String SQL_FIND_ALL = "SELECT * FROM users;";
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE id=?;";

    public UsersRepositoryJdbcImpl(HikariDataSource dataSource) {
        this.ds = dataSource;
    }


    @Override
    public User findById(Long id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            User findUser = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String userEmail = resultSet.getString("email");
                findUser = new User(userId, userEmail);
            }
            resultSet.close();
            return findUser;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            List<User> listAll = new LinkedList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listAll.add(new User(resultSet.getLong("id"), resultSet.getString("email")));
            }
            return listAll;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setString(1, entity.getEmail());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setLong(2, entity.getId());
            statement.setString(1, entity.getEmail());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_EMAIL)) {
            statement.setString(2, email);
            User findUser = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String userEmail = resultSet.getString("email");
                findUser = new User(userId, userEmail);
            }
            resultSet.close();
            if (findUser != null) {
                return Optional.of(findUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
