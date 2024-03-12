/**
 * @file: Cliente.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 16:15:58
 */
package com.turismo.grupoIII.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCliente")
public class Cliente {
	
	/**
     * El campo 'idCliente' es el identificador único del cliente.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	/**
     * El campo 'dni' es el Dni único del cliente.
     */
	@NotBlank(message = "El DNI no puede estar en blanco")
	@NotNull(message = "El DNI no puede estar nulo")
	@Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 dígitos numéricos")
	@Column(unique = true)
	private String dni;

	/**
     * El campo 'nombres' representa el nombre del cliente.
     */
	@NotBlank(message = "El nombre no puede estar en blanco")
	@NotNull(message = "El nombre no puede estar nulo")
	@Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)?$", message = "Debe contener uno o dos nombres separados por un espacio y sin números")
	private String nombres;

	/**
     * El campo 'apellidoPaterno' representa el apellidoPaterno del cliente.
     */
	@NotBlank(message = "El apellido paterno no puede estar en blanco")
	@NotNull(message = "El apellido paterno no puede estar nulo")
	@Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
	private String apellidoPaterno;

	/**
     * El campo 'apellidoMaterno' representa el apellidoMaterno del cliente.
     */
	@NotBlank(message = "El apellido materno no puede estar en blanco")
	@NotNull(message = "El apellido materno no puede estar nulo")
	@Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 100 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Debe contener solo letras")
	private String apellidoMaterno;

	/**
     * El campo 'correo' representa el correo del cliente.
     */
	@NotBlank(message = "El correo no puede estar en blanco")
	@NotNull(message = "El correo no puede estar nulo")
	@Email(message = "El formato del correo electrónico no es válido")
	private String correo;

	 /**
     * El campo 'telefono' representa el telefono del cliente.
     */
	@NotBlank(message = "El teléfono no puede estar en blanco")
	@NotNull(message = "El teléfono no puede estar nulo")
	@Pattern(regexp = "^9[0-9]{8}$", message = "El número de teléfono debe iniciar con 9 y tener un total de 9 dígitos")
	private String telefono;
	  /**
     * El campo 'pais' representa el pais del cliente.
     */
	@NotBlank(message = "El país de procedencia no debe estar en blanco")
	@NotNull(message = "El país de procedencia no puede estar nulo")
	@Pattern(regexp = "^([a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?( [a-zA-Z]+( [a-zA-Z]+)?)?$", 
	        message = "Solo se pueden ingresar letras")
	private String pais;
	  /**
     * El campo 'fechaNacimiento' representa el dia de nacimiento del cliente.
     */
	@NotNull(message = "La fecha de nacimiento no puede estar en blanco")
	@Past(message = "La fecha de nacimiento debe ser en el pasado")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	  /**
     * El campo 'genero' representa el genero del cliente.
     */
	@NotBlank(message = "El género no puede estar en blanco")
	@NotNull(message = "El género no puede estar nulo")
	@Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El género debe ser Masculino, Femenino u Otro")
	private String genero;
    
    @OneToMany(mappedBy = "cliente")
    private List<Contrato> contratos = new ArrayList<>();
}
