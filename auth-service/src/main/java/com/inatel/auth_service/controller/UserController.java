package com.inatel.auth_service.controller;

import com.inatel.auth_service.dto.BanStatusDTO;
import com.inatel.auth_service.dto.RoleUpdateDTO;
import com.inatel.auth_service.dto.UserRegisterDTO;
import com.inatel.auth_service.dto.UserUpdateDTO;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.mapper.UserMapper;
import com.inatel.auth_service.service.UserService;
import com.inatel.auth_service.validator.PasswordMatchValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterDTO dto) {
        PasswordMatchValidator.comparePasswords(dto.password(), dto.confirmPassword());
        User newUser = userMapper.toEntity(dto);
        service.register(newUser);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable String id) {
        var userId = UUID.fromString(id);
        User user = service.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCredentials(
            @PathVariable String id,
            @RequestBody UserUpdateDTO dto
    ) {

        var userId = UUID.fromString(id);
        User user = service.findById(userId);

        PasswordMatchValidator.comparePasswords(dto.password(), dto.confirmPassword());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        service.updateCredentials(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ban")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateBannedStatus(
            @PathVariable String id,
            @RequestBody @Valid BanStatusDTO dto) {
        var userId = UUID.fromString(id);
        User user = service.findById(userId);

        user.setBanned(dto.banned());
        service.updateBannedStatus(user);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable String id,
            @RequestBody @Valid RoleUpdateDTO dto) {
        var userId = UUID.fromString(id);
        User user = service.findById(userId);

        user.setRole(dto.role());
        service.updateUserRole(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        var userId = UUID.fromString(id);
        service.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}