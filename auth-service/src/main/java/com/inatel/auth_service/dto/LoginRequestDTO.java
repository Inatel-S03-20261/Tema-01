package com.inatel.auth_service.dto;

public record LoginRequestDTO(
        // Validate fields
        String username,
        String password
) {
}
