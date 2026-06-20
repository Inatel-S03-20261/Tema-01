package com.inatel.auth_service.controller;

import com.inatel.auth_service.dto.LoginRequestDTO;
import com.inatel.auth_service.dto.TokenDTO;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.service.JwtService;
import com.inatel.auth_service.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
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
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        Optional<User> userOptional = userService.findByUsernameOrEmail(dto.usernameOrEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(dto.password(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                String type = "Bearer";
                long expiresIn = jwtService.parseToken(token).getExpiration().getTime();

                return ResponseEntity.ok(new TokenDTO(token, type, expiresIn));
            }
        }
        // Bad credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("validate")
    public ResponseEntity<Claims> validateToken(@RequestBody TokenDTO dto) {
        Claims claims = null;
        try {
            claims = jwtService.parseToken(dto.token());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO dto) {
        Claims claims = null;
        try {
            claims = jwtService.parseToken(dto.token());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtService.generateToken(claims.getSubject());
        String type = "Bearer";
        long expiresIn = jwtService.parseToken(token).getExpiration().getTime();

        return ResponseEntity.ok(new TokenDTO(token, type, expiresIn));
    }
}
