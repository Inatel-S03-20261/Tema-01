package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.exception.UserNotFoundException;
import com.inatel.auth_service.repository.UserRepository;
import com.inatel.auth_service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserValidator validator;

    public User register(User user) {
        validator.validate(user);
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = repository.save(user);

        return repository.save(saved);
    }

    public void updateCredentials(User user) {
        validator.validate(user);
        repository.save(user);
    }

    public User findById(UUID id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return userOptional.get();
    }

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void deleteById(UUID id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        repository.deleteById(id);
    }
}