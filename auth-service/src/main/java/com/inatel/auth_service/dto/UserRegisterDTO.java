package com.inatel.auth_service.dto;

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
