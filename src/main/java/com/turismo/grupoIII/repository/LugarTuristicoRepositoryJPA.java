/**
 * @file: LugarTuristicoRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:58:40
 */
package com.turismo.grupoIII.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turismo.grupoIII.domain.LugarTuristico;

/**
 * Repositorio JPA para la entidad LugarTuristico.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los LugarTuristico en la base de datos.
 */
public interface LugarTuristicoRepositoryJPA extends JpaRepository<LugarTuristico, Long>{

}
