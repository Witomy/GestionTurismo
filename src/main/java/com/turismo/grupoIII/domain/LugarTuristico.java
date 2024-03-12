/**
 * @file: LugarTuristico.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 18:38:11
 */
package com.turismo.grupoIII.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLugar")
public class LugarTuristico {

	 /**
     * Identificador único del lugar turístico.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Temporal(TemporalType.TIME)
    private Date horaApertura;

    /**
     * Hora de cierre del lugar turístico.
     */
    @NotNull(message = "La hora de cierre no puede estar nula")
    @Temporal(TemporalType.TIME)
    private Date horaCierre;
    
    /**
     * Detalle de la ruta turística a la que pertenece el lugar turístico.
     */
    @ManyToOne
    @JoinColumn(name = "detalle_ruta_id")
    private DetalleRutaTuristica detalleRuta;
	
}
