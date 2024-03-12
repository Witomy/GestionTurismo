/**
 * @file: EmpleadoDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:39:17
 */
package com.turismo.grupoIII.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para un empleado.
 * Esta clase extiende RepresentationModel<EmpleadoDTO>, lo que permite agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de EmpleadoDTO.
 */
@Data

/*
 * Al extender RepresentationModel<EmpleadoDTO>, la clase EmpleadoDTO hereda la capacidad de agregar enlaces HATEOAS a sus instancias.
 * Esto significa que puedes agregar enlaces relacionados con los recursos de cliente a una instancia de EmpleadoDTO
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEmpleado")
public class EmpleadoDTO extends RepresentationModel<EmpleadoDTO>{
    /**
     * Identificador único del empleado.
     */
    private Long idEmpleado;

    /**
     * Número de identificación del empleado (DNI).
     */
    @NotBlank(message = "El DNI no puede estar en blanco")
    @NotNull(message = "El DNI no puede estar nulo")
    @Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 dígitos numéricos")
    @Column(unique = true)
    private String dni;

    /**
     * Nombres del empleado.
     */
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede estar nulo")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)?$", message = "Debe contener uno o dos nombres separados por un espacio y sin números")
    private String nombres;

    /**
     * Apellido paterno del empleado.
     */
    @NotBlank(message = "El apellido paterno no puede estar en blanco")
    @NotNull(message = "El apellido paterno no puede estar nulo")
    @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
    private String apellidoPaterno;

    /**
     * Apellido materno del empleado.
     */
    @NotBlank(message = "El apellido materno no puede estar en blanco")
    @NotNull(message = "El apellido materno no puede estar nulo")
    @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
    private String apellidoMaterno;

    /**
     * Número de teléfono del empleado.
     */
    @NotBlank(message = "El teléfono no puede estar en blanco")
    @NotNull(message = "El teléfono no puede estar nulo")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de teléfono debe iniciar con 9 y tener un total de 9 dígitos")
    private String telefono;

    /**
     * Dirección del empleado.
     */
    @NotBlank(message = "La dirección no debe estar en blanco")
    @NotNull(message = "La dirección no puede estar nulo")
    private String direccion;

    /**
     * Fecha de nacimiento del empleado.
     */
    @NotNull(message = "La fecha de nacimiento no puede estar en blanco")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    /**
     * Género del empleado.
     */
    @NotBlank(message = "El género no puede estar en blanco")
    @NotNull(message = "El género no puede estar nulo")
    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El género debe ser Masculino, Femenino u Otro")
    private String genero;

    /**
     * Lista de contratos asociados al empleado.
     */
    private List<ContratoDTO> contratos = new ArrayList<>();

    /**
     * Empleado jefe del empleado.
     */
    private EmpleadoDTO jefe;
}
