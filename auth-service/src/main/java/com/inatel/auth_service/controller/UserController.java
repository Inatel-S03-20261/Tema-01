package com.inatel.auth_service.controller;

import com.inatel.auth_service.dto.UserRegisterDTO;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.mapper.UserMapper;
import com.inatel.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterDTO dto) {
        User newUser = userMapper.toEntity(dto);
        service.register(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUsuario(
            @PathVariable UUID id,
            @RequestBody User request
    ) {
        return ResponseEntity.ok(service.atualizarUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}