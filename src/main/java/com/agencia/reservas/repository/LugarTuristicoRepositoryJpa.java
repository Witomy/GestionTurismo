/**
 * @file: LugarTuristicoRepositoryJpa.java
 * @autor: Jheferson Chalan
 * @creado: 4 mar. 2024 12:14:31
 */
package com.agencia.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencia.reservas.domain.LugarTurisitico;

/**
 * 
 */
@Repository
public interface LugarTuristicoRepositoryJpa extends JpaRepository<LugarTurisitico, Long> {

}
