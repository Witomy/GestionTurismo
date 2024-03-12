/**
 * @file: RutaTuristicaDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:39:56
 */
package com.turismo.grupoIII.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para una ruta turística.
 * Esta clase extiende RepresentationModel<RutaTuristicaDTO>, lo que permite agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de RutaTuristicaDTO.
 */
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRuta")
public class RutaTuristicaDTO extends RepresentationModel<RutaTuristicaDTO> {
	
	/**
     * Identificador único de la ruta turística.
     */
    private Long idRuta;

    /**
     * Nombre de la ruta turística.
     */
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "El nombre debe contener solo letras y espacios")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede estar nulo")
    private String nombre;

    /**
     * Duración de la ruta turística en horas.
     */
    @Min(value = 1, message = "La duración debe ser al menos 1")
    @Digits(integer = 5, fraction = 0, message = "La duración debe ser un número entero válido")
    private int duracion;

    /**
     * Costo por persona de la ruta turística.
     */
    @NotNull(message = "El costo total no puede estar en blanco")
    @Positive(message = "El costo total debe ser un número positivo")
    @Digits(integer = 5, fraction = 0, message = "El costo debe ser un entero")
    private int costoPersona;

    /**
     * Cantidad de lugares turísticos incluidos en la ruta.
     */
    @Min(value = 1, message = "La cantidad de lugares debe ser al menos 1")
    @Digits(integer = 5, fraction = 0, message = "La cantidad de lugares debe ser un número entero válido")
    private int cantidadLugares;

    /**
     * Lista de contratos asociados a la ruta turística.
     */
    private List<ContratoDTO> contratos;

    /**
     * Detalle de la ruta turística.
     */
    private DetalleRutaTuristicaDTO detalleRutaTuristica;
}
