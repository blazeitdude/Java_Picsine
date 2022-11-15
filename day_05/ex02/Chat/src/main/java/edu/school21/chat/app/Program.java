package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5555/postgres");
            hikariConfig.setUsername("postgres");
            hikariConfig.setPassword("postgres");
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(hikariDataSource);

            {
                User creator = new User(1L, "user", "user", new ArrayList<>(), new ArrayList<>());
                User author = creator;
                Chatroom room = new Chatroom(5L, "room", creator, new ArrayList<>());
                Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
                messagesRepository.save(message);
                System.out.println("New message ID: " + message.getId());
            }
            System.out.println("------------------------");
            System.out.println("Exceptions:");
            try {
                User creator = new User(7L, "user", "user", new ArrayList<>(), new ArrayList<>());
                User author = creator;
                Chatroom room = new Chatroom(8L, "room", creator, new ArrayList<>());
                Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
                messagesRepository.save(message);
                System.out.println(message.getId());
            } catch (NotSavedSubEntityException e) {
                e.printStackTrace();
            }

            try {
                User creator = new User(7L, "user", "user", new ArrayList<>(), new ArrayList<>());
                User author = creator;
                Chatroom room = new Chatroom(1L, "room", creator, new ArrayList<>());
                Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
                messagesRepository.save(message);
                System.out.println(message.getId());
            } catch (NotSavedSubEntityException e) {
                e.printStackTrace();
            }

            try {
                Message message = new Message(null, null, null, "Hello!", LocalDateTime.now());
                messagesRepository.save(message);
                System.out.println(message.getId());
            } catch (NotSavedSubEntityException e) {
                e.printStackTrace();
            }

            hikariDataSource.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}