package com.inatel.auth_service.dto;

import com.inatel.auth_service.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@PasswordMatches
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "username é obrigatório")
    private String username;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    private String email;

    @NotBlank(message = "password é obrigatório")
    @Size(min = 8, message = "password deve ter ao menos 8 caracteres")
    private String password;

    @NotBlank(message = "confirmPassword é obrigatório")
    private String confirmPassword;

    @Past(message = "dob deve ser uma data passada")
    private LocalDate dob;
}