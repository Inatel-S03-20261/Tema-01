package com.inatel.auth_service.dto;

public record TokenResponseDTO(
        String token,
        String type,
        long expiresIn
) {
}