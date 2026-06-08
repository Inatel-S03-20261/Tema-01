package com.inatel.auth_service.controller;

import com.inatel.auth_service.dto.LoginRequestDTO;
import com.inatel.auth_service.dto.TokenResponseDTO;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.service.JwtService;
import com.inatel.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        Optional<User> userOptional = userService.findByUsernameOrEmail(dto.usernameOrEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(dto.password(), user.getPassword())){
                String token = jwtService.generateToken("");
                String type = "Bearer";
                long expiresIn = 1L;

                return ResponseEntity.ok(new TokenResponseDTO(token, type, expiresIn));
            }
        }
        // Bad credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
