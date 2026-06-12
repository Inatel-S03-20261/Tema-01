package com.inatel.auth_service.dto;

public record UserUpdateDTO(
        // Validate
        String email,
        String password,
        String confirmPassword
) {
}
