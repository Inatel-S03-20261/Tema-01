package com.inatel.auth_service.controller;

import java.time.LocalDate;

public record UserRegisterDTO(
        String username,
        String email,
        String password,
        String confirmPassword,
        LocalDate dob
) {
}
