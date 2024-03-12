/**
 * @file: Contrato.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 16:37:00
 */
package com.turismo.grupoIII.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idContrato")
public class Contrato {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

	/**
     * Fecha en la que el contrato debe iniciar. Debe ser en el futuro.
     */

	@NotNull(message = "La fecha de inicio del contrato no puede estar nula")
	@Future(message = "La fecha de inicio del contrato debe estar en el futuro")
	@Temporal(TemporalType.DATE)
	private Date fechaDeInicio;
	/**
     * Fecha en la que el contrato debe finalizar. Debe ser en el futuro.
     */
	@NotNull(message = "La fecha de fin del contrato no puede estar nula")
	@Future(message = "La fecha de fin del contrato debe estar en el futuro")
	@Temporal(TemporalType.DATE)
	private Date fechaDeFin;
	 /**
     * Cantidad de personas asociadas al contrato. Debe ser un número entero positivo.
     */
	@NotNull(message = "La cantidad de personas no puede estar en blanco")
	@Positive(message = "La cantidad de personas debe ser un número positivo")
	@Digits(integer = 2, fraction = 0, message = "La cantidad de personas debe ser un número entero válido")
	private Integer cantidadDePersonas;
	  /**
     * Costo total del contrato. Debe ser un número entero positivo.
     */
	@NotNull(message = "El costo total no puede estar en blanco")
	@Positive(message = "El costo total debe ser un número positivo")
	@Digits(integer = 5, fraction = 0, message = "El costo total debe ser entero")
	private int costoTotal;
	 /**
     * Cliente asociado al contrato.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    /**
     * Empleado encargado del contrato.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
    /**
     * Lista de rutas turísticas asociadas al contrato.
     */
    @ManyToMany
    @JoinTable(
            name = "contrato_ruta_turistica", 
            joinColumns = @JoinColumn(name = "contrato_id"), 
            inverseJoinColumns = @JoinColumn(name = "ruta_turistica_id")) 
    private List<RutaTuristica> rutasTuristicas = new ArrayList<>();
}
