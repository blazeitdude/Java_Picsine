package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.util.Optional;

public class Program {
    public static void main(String[] args) throws SQLException {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5555/postgres");
            hikariConfig.setUsername("postgres");
            hikariConfig.setPassword("postgres");
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(hikariDataSource);

            {
                Optional<Message> messageOptional = messagesRepository.findById(5L);
                if (messageOptional.isPresent()) {
                    Message message = messageOptional.get();
                    message.setText("Bye");
                    message.setDateTime(null);
                    messagesRepository.update(message);

                }
            }
            System.out.println("DB already updated");
            System.out.println("--------------------------------");
            System.out.println("👇Exceptions:");
            try {
                User tmpU = new User(21L, "user21", "user21_password", null, null);
                Chatroom tmpR = new Chatroom(21L, "newroom", tmpU, null);
                Message tmpM = new Message(21L, tmpU, tmpR, "21 message", null);
                tmpM.setText("Bye");
                tmpM.setDateTime(null);
                messagesRepository.update(tmpM);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

            try {
                messagesRepository.update(null);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

            try {
                User tmpU = new User(5L, "user21", "user21_password", null, null);
                Chatroom tmpR = new Chatroom(21L, "newroom", tmpU, null);
                Message tmpM = new Message(5L, tmpU, tmpR, "21 message", null);
                tmpM.setText("Bye");
                tmpM.setDateTime(null);
                messagesRepository.update(tmpM);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

            try {
                User tmpU = new User(21L, "user21", "user21_password", null, null);
                Chatroom tmpR = new Chatroom(5L, "newroom", tmpU, null);
                Message tmpM = new Message(5L, tmpU, tmpR, "21 message", null);
                tmpM.setText("Bye");
                tmpM.setDateTime(null);
                messagesRepository.update(tmpM);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

            hikariDataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }
}
