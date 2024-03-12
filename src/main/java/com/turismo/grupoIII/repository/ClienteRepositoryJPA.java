/**
 * @file: ClienteRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:23:09
 */
package com.turismo.grupoIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turismo.grupoIII.domain.Cliente;

/**
 * Repositorio JPA para la entidad Cliente.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los clientes en la base de datos.
 */
@Repository
public interface ClienteRepositoryJPA extends JpaRepository<Cliente, Long> {
	boolean existsBydni(String dni);
	List<Cliente> findBydni(String dni);
}
