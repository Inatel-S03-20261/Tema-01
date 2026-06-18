package com.inatel.auth_service.dto;

import com.inatel.auth_service.entity.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDTO(@NotNull Role role) {
}
