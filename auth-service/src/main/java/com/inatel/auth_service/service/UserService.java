package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User newUser) {
        String email = newUser.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }
        User user = User.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .dob(newUser.getDob())
                .role(newUser.getRole())
                .banned(newUser.getBanned())
                .build();

        return userRepository.save(user);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }

    public User atualizarUsuario(UUID id, User dadosAtualizados) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        user.setName(dadosAtualizados.getName());
        user.setEmail(dadosAtualizados.getEmail());

        return userRepository.save(user);
    }

    public List<User> listarUsuarios(){

        return userRepository.findAll();
    }
    public void deletarUsuario(UUID id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}