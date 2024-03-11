/**
 * @file: DetalleReservaRepositoryJpa.java
 * @autor: Jheferson Chalan
 * @creado: 4 mar. 2024 12:13:43
 */
package com.agencia.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencia.reservas.domain.DetalleReserva;

/**
 * 
 */
@Repository
public interface DetalleReservaRepositoryJpa extends JpaRepository<DetalleReserva, Long> {

}
