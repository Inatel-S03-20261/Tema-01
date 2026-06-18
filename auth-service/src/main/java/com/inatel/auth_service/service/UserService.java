package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.Role;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.event.UserCreatedEvent;
import com.inatel.auth_service.exception.UserNotFoundException;
import com.inatel.auth_service.repository.UserRepository;
import com.inatel.auth_service.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher events;

    @Transactional
    public User register(User user) {
        validator.validate(user);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.PLAYER);
        user.setBanned(false);
        User saved = repository.save(user);

        events.publishEvent(UserCreatedEvent.from(saved));
        return saved;
    }

    public void updateCredentials(User user) {
        validator.validate(user);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void updateBannedStatus(User user) {
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

    public Role getRole(String username) {
        User user = repository.findByUsernameOrEmail(username, username).get();
        return user.getRole();
    }
}