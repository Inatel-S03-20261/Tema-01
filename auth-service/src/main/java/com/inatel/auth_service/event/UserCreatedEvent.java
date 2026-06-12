package com.inatel.auth_service.event;

import com.inatel.auth_service.entity.User;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(UUID id, String email, Instant createdAt) {

    public static UserCreatedEvent from(User user) {
        return new UserCreatedEvent(user.getId(), user.getEmail(), Instant.now());
    }
}
