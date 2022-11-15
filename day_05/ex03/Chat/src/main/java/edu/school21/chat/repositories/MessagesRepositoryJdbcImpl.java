package edu.school21.chat.repositories;

import edu.school21.chat.app.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
    private final DataSource dataSource;

    private final String SQL_FIND_MESSAGE_BY_ID = "SELECT \n" +
            "messages.id AS message_id,\n" +
            "users.id AS user_id,\n" +
            "users.login AS user_login,\n" +
            "users.password AS user_password,\n" +
            "chatrooms.id AS chatroom_id,\n" +
            "chatrooms.name AS chatroom_name,\n" +
            "messages.text AS message_text,\n" +
            "messages.date_time AS message_date_time\n" +
            "FROM messages \n" +
            "LEFT JOIN users ON users.id = messages.author \n" +
            "LEFT JOIN chatrooms ON chatrooms.id = messages.room\n" +
            "WHERE messages.id = ? ;";
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?;";
    private final String SQL_FIND_ROOM_BY_ID = "SELECT * FROM chatrooms WHERE id=?;";
    private final String SQL_SAVE_MESSAGE = "INSERT INTO messages (author, room, text, date_time) VALUES(?,?,?,?);";
    private final String SQL_UPDATE_MESSAGE = "UPDATE messages SET author=?, room=?, text=?, date_time=? WHERE id=?;";


    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id){
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_MESSAGE_BY_ID)) {
            statement.setLong(1, id);
            Message findMessage = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong(("user_id"));
                String userLogin = resultSet.getString("user_login");
                String userPassword = resultSet.getString("user_password");
                User findUser = new User(userId, userLogin, userPassword, null, null);
                Long chatroomId = resultSet.getLong("chatroom_id");
                String chatroomName = resultSet.getString("chatroom_name");
                Chatroom findChatroom = new Chatroom(chatroomId, chatroomName, null, null);
                Long message_id = resultSet.getLong("message_id");
                String messageText = resultSet.getString("message_text");
                LocalDateTime messageDateTime;
                Object findMessageDateTime = resultSet.getObject("message_date_time");
                if (findMessageDateTime == null)
                    messageDateTime = null;
                else
                    messageDateTime = resultSet.getTimestamp("message_date_time").toLocalDateTime();
                findMessage = new Message(message_id, findUser, findChatroom, messageText, messageDateTime);
            }
            resultSet.close();
            if (findMessage != null)
                return Optional.of(findMessage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        if (message.getRoom() == null) {
            throw new NotSavedSubEntityException("Room ID: is null");
        }
        if (message.getAuthor() == null) {
            throw new NotSavedSubEntityException("Author ID: is null");
        }
        if (!findRoomById(message.getRoom().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Room ID: " + message.getRoom().getId() + " not found");
        }
        if (!findUserById(message.getAuthor().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Author ID: " + message.getAuthor().getId() + " not found");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_MESSAGE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Long i = null;
            if (resultSet.next()) {
                i = resultSet.getLong(1);
            }
            if (i != null) {
                message.setId(i);
            } else {
                throw new NotSavedSubEntityException("Generated ID is null");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message message) {
        if (message == null){
            throw new NotSavedSubEntityException("Message is null");
        }
        if (message.getId() == null){
            throw new NotSavedSubEntityException("Message ID: is null");
        }
        if (message.getRoom() == null) {
            throw new NotSavedSubEntityException("Room ID: is null");
        }
        if (message.getAuthor() == null) {
            throw new NotSavedSubEntityException("Author ID: is null");
        }
        if (!findById(message.getId()).isPresent()) {
            throw new NotSavedSubEntityException("Message ID: " + message.getId() + " not found");
        }
        if (!findRoomById(message.getRoom().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Room ID: " + message.getRoom().getId() + " not found");
        }
        if (!findUserById(message.getAuthor().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Author ID: " + message.getAuthor().getId() + " not found");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MESSAGE)) {
            statement.setLong(5, message.getId());
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());

            if (message.getText() == null) {
                statement.setNull(3, Types.VARCHAR);
            } else {
                statement.setString(3, message.getText());
            }
            if (message.getDateTime() == null) {
                statement.setNull(4, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            }
            statement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private Optional<User> findUserById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            User findUser = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String userLogin = resultSet.getString("login");
                String userPassword = resultSet.getString("password");
                findUser = new User(userId, userLogin, userPassword, null, null);
            }
            resultSet.close();
            if (findUser != null) {
                return Optional.of(findUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<Chatroom> findRoomById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROOM_BY_ID)) {
            statement.setLong(1, id);
            Chatroom findRoom = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long chatroomId = resultSet.getLong("id");
                String chatroomName = resultSet.getString("name");
                findRoom = new Chatroom(chatroomId, chatroomName, null, null);
            }
            resultSet.close();
            if (findRoom != null) {
                return Optional.of(findRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
