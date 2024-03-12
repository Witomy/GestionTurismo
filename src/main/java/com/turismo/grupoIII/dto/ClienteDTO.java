/**
 * @file: ClienteDTO.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:46:45
 */
package com.turismo.grupoIII.dto;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clase que representa un DTO (Data Transfer Object) para el cliente.
 * Esta clase extiende RepresentationModel<ClienteDTO> para agregar enlaces HATEOAS a sus instancias.
 * Los enlaces HATEOAS permiten agregar enlaces relacionados con los recursos de cliente a una instancia de ClienteDTO.
 */
@Data

/*
 * Al extender RepresentationModel<ClienteDTO>, la clase ClienteDTO hereda la capacidad de agregar enlaces HATEOAS a sus instancias.
 * Esto significa que puedes agregar enlaces relacionados con los recursos de cliente a una instancia de ClienteDTO
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCliente")
public class ClienteDTO extends RepresentationModel<ClienteDTO>{
    /**
     * Identificador único del cliente.
     */
    private Long idCliente;

    /**
     * DNI del cliente.
     */
    @NotBlank(message = "El DNI no puede estar en blanco")
    @NotNull(message = "El DNI no puede estar nulo")
    @Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 dígitos numéricos")
    @Column(unique = true)
    private String dni;

    /**
     * Nombres del cliente.
     */
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede estar nulo")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)?$", message = "Debe contener uno o dos nombres separados por un espacio y sin números")
    private String nombres;

    /**
     * Apellido paterno del cliente.
     */
    @NotBlank(message = "El apellido paterno no puede estar en blanco")
    @NotNull(message = "El apellido paterno no puede estar nulo")
    @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
    private String apellidoPaterno;

    /**
     * Apellido materno del cliente.
     */
    @NotBlank(message = "El apellido materno no puede estar en blanco")
    @NotNull(message = "El apellido materno no puede estar nulo")
    @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
    private String apellidoMaterno;

    /**
     * Correo electrónico del cliente.
     */
    @NotBlank(message = "El correo no puede estar en blanco")
    @NotNull(message = "El correo no puede estar nulo")
    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    /**
     * Número de teléfono del cliente.
     */
    @NotBlank(message = "El teléfono no puede estar en blanco")
    @NotNull(message = "El teléfono no puede estar nulo")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de teléfono debe iniciar con 9 y tener un total de 9 dígitos")
    private String telefono;

    /**
     * País de procedencia del cliente.
     */
    @NotBlank(message = "El país de procedencia no debe estar en blanco")
    @NotNull(message = "El país de procedencia no puede estar nulo")
    @Pattern(regexp = "^([a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?$", 
            message = "Solo se pueden ingresar letras")
    private String pais;

    /**
     * Fecha de nacimiento del cliente.
     */
    @NotNull(message = "La fecha de nacimiento no puede estar en blanco")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private Date fechaNacimiento;

    /**
     * Género del cliente.
     */
    @NotBlank(message = "El género no puede estar en blanco")
    @NotNull(message = "El género no puede estar nulo")
    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El género debe ser Masculino, Femenino u Otro")
    private String genero;

    /**
     * Lista de contratos asociados al cliente.
     */
    private List<ContratoDTO> contratos;
}