package com.inatel.auth_service.controller;

import com.inatel.auth_service.dto.UserRegisterDTO;
import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.mapper.UserMapper;
import com.inatel.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> listarUsuarios() {
        return ResponseEntity.ok("Endpoint para listar usuários");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok("Endpoint para buscar usuário por ID: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Object request
    ) {
        return ResponseEntity.ok("Endpoint para atualizar usuário por ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}