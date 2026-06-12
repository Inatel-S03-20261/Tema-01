package com.inatel.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users") 
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50) 
    private String username;

    @Column(nullable = false, unique = true, length = 100) 
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private Boolean banned;

    @CreatedDate // O Spring preenche automaticamente no primeiro INSERT
    @Column(name = "created_at", updatable = false) 
    private LocalDateTime createdAt;

    @LastModifiedDate // O Spring atualiza automaticamente a cada UPDATE
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}