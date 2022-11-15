package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, MessageRepository messageRepository,
                            PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signUp(String login, String password) {
        Optional<User> optionalUser = usersRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return false;
        }
        usersRepository.save(new User(login, passwordEncoder.encode(password)));
        return true;
    }

    @Override
    public boolean signIn(String login, String password) {
        Optional<User> user = usersRepository.findByLogin(login);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void sendMessage(String login, String message) {
            messageRepository.save(
                    new Message(null, usersRepository.findByLogin(login).get(),
                            null, message, LocalDateTime.now())
            );
    }
}
