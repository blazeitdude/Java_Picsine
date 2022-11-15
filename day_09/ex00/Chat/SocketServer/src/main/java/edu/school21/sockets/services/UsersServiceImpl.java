package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signUp(String login, String password) {
        Optional<User> optionalUser = repository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return false;
        }
        repository.save(new User(login, passwordEncoder.encode(password)));
        return true;
    }
}
