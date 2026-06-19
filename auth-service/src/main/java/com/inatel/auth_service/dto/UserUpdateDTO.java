package com.inatel.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Email @Size(max = 100) String email,
        @Size(min = 8) String password,
        String confirmPassword
) {
}
