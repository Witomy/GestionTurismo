/**
 * @file: ReservaRepositoryJpa.java
 * @autor: Jheferson Chalan
 * @creado: 4 mar. 2024 12:16:22
 */
package com.agencia.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencia.reservas.domain.Reserva;

/**
 * 
 */
@Repository
public interface ReservaRepositoryJpa extends JpaRepository<Reserva, Long> {

}
