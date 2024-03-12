/**
 * @file: DetalleRutaTuristica.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 21:22:01
 */
package com.turismo.grupoIII.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

/**
 * Clase que representa un detalle de una ruta turística.
 */

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDetalleRuta")
public class DetalleRutaTuristica {
	
	 /**
     * Identificador único del detalle de la ruta.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleRuta;
	
	 /**
     * Ruta turística asociada al detalle.
     */
	@OneToOne
    private RutaTuristica rutaTuristica;
	
	  /**
     * Lista de lugares turísticos asociados al detalle de la ruta.
     */
	@OneToMany(mappedBy = "detalleRuta", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<LugarTuristico> lugaresTuristicos = new ArrayList<>();
}
