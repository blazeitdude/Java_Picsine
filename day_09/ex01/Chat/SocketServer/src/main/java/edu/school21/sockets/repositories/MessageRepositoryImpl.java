package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryImpl implements MessageRepository {
    private final JdbcTemplate jdbcTemplate;

    public MessageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String FIND_ALL = "SELECT * FROM messages";
    private static final String FIND_MESSAGE_BY_ID_SQL = FIND_ALL + " WHERE user_id = ?";
    private static final String UPDATE_MESSAGE_SQL = new StringBuffer()
            .append("UPDATE author_id ")
            .append("SET chatroom_id = ? ,")
            .append("message = ? ")
            .append("message_date = ? ")
            .append("WHERE message_id = ?").toString();

    private static final String SAVE_MESSAGE_SQL = new StringBuffer()
            .append("INSERT INTO messages (author_id, chatroom_id, message, message_date) ")
            .append("VALUES (?, ?, ?, ?)").toString();

    private static final String DELETE_MESSAGE_SQL = "DELETE FROM messages WHERE message_id = ?";

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void save(Message message) {
        jdbcTemplate.update(SAVE_MESSAGE_SQL,
                message.getAuthor().getId(), null,
                message.getMessage(), message.getLocalDateTime());
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_MESSAGE_SQL, id);
    }
}
