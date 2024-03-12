/**
 * @file: RutaTuristicaRepositoryJPA.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:58:58
 */
package com.turismo.grupoIII.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turismo.grupoIII.domain.RutaTuristica;


/**
 * Repositorio JPA para la entidad RutaTuristica.
 * Proporciona métodos para realizar operaciones de persistencia y consulta relacionadas con los RutaTuristica en la base de datos.
 */
public interface RutaTuristicaRepositoryJPA extends JpaRepository<RutaTuristica, Long> {

}
