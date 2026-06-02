package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void register(User user) {
        // Check if user already exists
        // Encode user password
        // Set 'role' as PLAYER
        // Set 'banned' as false
        // SQS
        repository.save(user);
    }
}
