/**
 * @file: DetalleRutaTuristicaRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:58:02
 */
package com.turismo.grupoIII.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turismo.grupoIII.domain.DetalleRutaTuristica;
/**
 * Repositorio JPA para la entidad DetalleRutaTuristica.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los DetalleRutaTuristica en la base de datos.
 */
public interface DetalleRutaTuristicaRepositoryJPA extends JpaRepository<DetalleRutaTuristica, Long>{

}
