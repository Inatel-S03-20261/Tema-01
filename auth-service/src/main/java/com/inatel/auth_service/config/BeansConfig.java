package com.inatel.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(10);}
}
