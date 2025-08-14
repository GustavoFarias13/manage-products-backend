package com.gustavofarias.manageproductsbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario") // Evita conflitos com palavras reservadas e deixa explícito
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50) // Limite de tamanho para performance
    private String username;

    @Column(nullable = false, length = 60) // 60 chars para senhas BCrypt
    private String password;

    @Column(nullable = false, length = 20) // ROLE_USER, ROLE_ADMIN, etc.
    private String role = "ROLE_USER";
}
