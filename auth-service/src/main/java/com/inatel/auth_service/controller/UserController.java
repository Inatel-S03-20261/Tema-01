package com.inatel.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Object request) {
        return ResponseEntity.ok("Endpoint para cadastrar usuário");
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