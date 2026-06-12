package com.inatel.auth_service.dto;

import com.inatel.auth_service.entity.Role;

import java.time.LocalDate;

public record UserRegisterDTO(
        // Validate fields
        String username,
        String email,
        String password,
        String confirmPassword,
        LocalDate dob
) {
}
