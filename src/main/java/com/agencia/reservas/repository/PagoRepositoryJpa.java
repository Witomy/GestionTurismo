/**
 * @file: PagoRepositoryJpa.java
 * @autor: Jheferson Chalan
 * @creado: 4 mar. 2024 12:15:39
 */
package com.agencia.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agencia.reservas.domain.Pago;

/**
 * 
 */
@Repository
public interface PagoRepositoryJpa extends JpaRepository<Pago, Long> {

}
