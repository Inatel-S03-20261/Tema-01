package com.inatel.auth_service.validator;

import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.exception.UserAlreadyExistsException;
import com.inatel.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validate(User user) {
        if(userExists(user)) {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    private boolean userExists(User user){
        Optional<User> foundUser = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        // If client doesn't provide an ID,
        // then they want to register a new user.
        if(user.getId() == null){
            return foundUser.isPresent();
        }

        // If client requests to update a user with
        // repeated username or email.
        return !user.getId().equals(foundUser.get().getId()) && foundUser.isPresent();
    }
}
