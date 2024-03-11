/**
 * @file: ClienteRepositoryJpa.java
 * @autor: Jheferson Chalan
 * @creado: 4 mar. 2024 09:21:21
 */
package com.agencia.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencia.reservas.domain.Cliente;

/**
 * 
 */
@Repository
public interface ClienteRepositoryJpa extends JpaRepository<Cliente, Long> {
	boolean existsByCorreo(String correo);
}
