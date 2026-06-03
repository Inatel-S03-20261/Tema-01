package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.User;
import com.inatel.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User new_user) {

        if (userRepository.existsByEmail(new_user.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        User user = User.builder()
                .name(new_user.getName())
                .email(new_user.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .build();

        return userRepository.save(user);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }

    public User atualizarUsuario(Long id, User dadosAtualizados) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        user.setName(dadosAtualizados.getName());

        user.setEmail(dadosAtualizados.getEmail());

        return userRepository.save(user);
    }

    public List<User> listarUsuarios(){
        // Lógica para listar os usuários
        return userRepository.findAll();
    }
    public void deletarUsuario(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException
        }
        userRepository.deleteById(id);
    }
}