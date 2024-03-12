/**
 * @file: DetalleRutaTuristicaDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:39:01
 */
package com.turismo.grupoIII.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para el detalle de una ruta turística.
 * Esta clase extiende RepresentationModel<DetalleRutaTuristicaDTO> para agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de DetalleRutaTuristicaDTO.
 */
@Data

/*
 * Al extender RepresentationModel<DetalleRutaTuristicaDTO>, la clase DetalleRutaTuristicaDTO hereda la capacidad de agregar enlaces HATEOAS a sus instancias.
 * Esto significa que puedes agregar enlaces relacionados con los recursos de cliente a una instancia de DetalleRutaTuristicaDTO
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDetalleRuta")
public class DetalleRutaTuristicaDTO extends RepresentationModel<DetalleRutaTuristicaDTO>{
	 /**
     * Identificador único del detalle de la ruta turística.
     */
    private Long idDetalleRuta;

    /**
     * Ruta turística asociada al detalle.
     */
    private RutaTuristicaDTO rutaTuristica;

    /**
     * Lista de lugares turísticos asociados al detalle de la ruta turística.
     */
    private List<LugarTuristicoDTO> lugaresTuristicos = new ArrayList<>();
}
