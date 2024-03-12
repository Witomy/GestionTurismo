/**
 * @file: EmpleadoRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:58:23
 */
package com.turismo.grupoIII.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Empleado;

/**
 * Repositorio JPA para la entidad Empleado.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los Empleado en la base de datos.
 */
public interface EmpleadoRepositoryJPA extends JpaRepository<Empleado, Long> {
	boolean existsBydni(String dni);
	List<Empleado> findBydni(String dni);
}
