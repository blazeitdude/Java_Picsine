package edu.school21.chat.repositories;

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
}
