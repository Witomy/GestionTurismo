/**
 * @file: LugarTuristicoDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:00:25
 */
package com.turismo.grupoIII.dto;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para un lugar turístico.
 * Esta clase extiende RepresentationModel<LugarTuristicoDTO>, lo que permite agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de LugarTuristicoDTO.
 */
@Data

/*
 * Al extender RepresentationModel<LugarTuristicoDTO>, la clase LugarTuristicoDTO hereda la capacidad de agregar enlaces HATEOAS a sus instancias.
 * Esto significa que puedes agregar enlaces relacionados con los recursos de cliente a una instancia de LugarTuristicoDTO
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLugar")
public class LugarTuristicoDTO extends RepresentationModel<LugarTuristicoDTO>{
	/**
     * Identificador único del lugar turístico.
     */
    private Long idLugar;

    /**
     * Nombre del lugar turístico.
     */
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede estar nulo")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El nombre debe contener solo letras y espacios")
    private String nombre;

    /**
     * Descripción del lugar turístico.
     */
    @NotBlank(message = "La descripción no puede estar en blanco")
    @NotNull(message = "La descripción no puede estar nula")
    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres")
    private String descripcion;

    /**
     * Hora de apertura del lugar turístico.
     */
    @NotNull(message = "La hora de apertura no puede estar nula")
    private Date horaApertura;

    /**
     * Hora de cierre del lugar turístico.
     */
    @NotNull(message = "La hora de cierre no puede estar nula")
    private Date horaCierre;

    /**
     * Detalle de la ruta turística asociada al lugar turístico.
     */
    private DetalleRutaTuristicaDTO detalleRuta;
}
