package com.inatel.auth_service.dto;

import jakarta.validation.constraints.NotNull;

public record BanStatusDTO(@NotNull Boolean banned) {
}
