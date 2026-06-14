package com.inatel.auth_service.dto;

public record TokenDTO(
        String token,
        String type,
        long expiresIn
) {
}