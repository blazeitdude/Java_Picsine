package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        System.out.print("Enter a message ID\n-> ");
        Scanner scanner = new Scanner(System.in);
        try {
            Long l = scanner.nextLong();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5555/postgres");
            hikariConfig.setUsername("postgres");
            hikariConfig.setPassword("postgres");
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(hikariDataSource);
            Optional<Message> result = messagesRepository.findById(l);
            if (result.isPresent()) {
                System.out.println(result.get());
            } else {
                System.out.println("Message with ID: " + l + " not found");
            }
            hikariDataSource.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        scanner.close();
    }
}
