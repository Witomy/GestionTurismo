/**
 * @file: ContratoDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:47:05
 */
package com.turismo.grupoIII.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para el contrato.
 * Esta clase extiende RepresentationModel<ContratoDTO> para agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de ContratoDTO.
 */
@Data

/*
 * Al extender RepresentationModel<ContratoDTO>, la clase ContratoDTO hereda la capacidad de agregar enlaces HATEOAS a sus instancias.
 * Esto significa que puedes agregar enlaces relacionados con los recursos de cliente a una instancia de ContratoDTO
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idContrato")
public class ContratoDTO extends RepresentationModel<ContratoDTO>{


    /**
     * Identificador único del contrato.
     */
    private Long idContrato;

    /**
     * Fecha de inicio del contrato.
     */
    @NotNull(message = "La fecha de inicio del contrato no puede estar nula")
    @Future(message = "La fecha de inicio del contrato debe estar en el futuro")
    private Date fechaDeInicio;

    /**
     * Fecha de fin del contrato.
     */
    @NotNull(message = "La fecha de fin del contrato no puede estar nula")
    @Future(message = "La fecha de fin del contrato debe estar en el futuro")
    private Date fechaDeFin;

    /**
     * Cantidad de personas incluidas en el contrato.
     */
    @NotNull(message = "La cantidad de personas no puede estar en blanco")
    @Positive(message = "La cantidad de personas debe ser un número positivo")
    private Integer cantidadDePersonas;

    /**
     * Costo total del contrato.
     */
    @NotNull(message = "El costo total no puede estar en blanco")
    @Positive(message = "El costo total debe ser un número positivo")
    @Digits(integer = 5, fraction = 0, message = "El costo total debe ser entero")
    private int costoTotal;

    /**
     * Cliente asociado al contrato.
     */
    private ClienteDTO cliente;

    /**
     * Empleado asociado al contrato.
     */
    private EmpleadoDTO empleado;

    /**
     * Lista de rutas turísticas asociadas al contrato.
     */
    private List<RutaTuristicaDTO> rutasTuristicas = new ArrayList<>();
}
