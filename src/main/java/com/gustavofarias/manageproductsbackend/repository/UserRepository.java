package com.gustavofarias.manageproductsbackend.repository;

import com.gustavofarias.manageproductsbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username nome de login do usuário.
     * @return um Optional contendo o usuário, caso encontrado.
     */
    Optional<User> findByUsername(String username);
}
