/**
 * @file: UserRepository.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:12:48
 */
package com.turismo.grupoIII.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turismo.grupoIII.domain.User;

/**
 * Repositorio para la entidad User.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario a buscar.
     * @return Optional que contiene el usuario encontrado, o vacío si no se encuentra.
     */
    Optional<User> findByUsername(String username); 
}