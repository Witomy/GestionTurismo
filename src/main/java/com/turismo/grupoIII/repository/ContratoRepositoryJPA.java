/**
 * @file: ContratoRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:24:19
 */
package com.turismo.grupoIII.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turismo.grupoIII.domain.Contrato;
/**
 * Repositorio JPA para la entidad Contrato.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los Contrato en la base de datos.
 */
@Repository
public interface ContratoRepositoryJPA extends JpaRepository<Contrato, Long> {

}
