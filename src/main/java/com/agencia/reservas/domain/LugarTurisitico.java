/**
 * @file: LugarTurisitico.java
 * @autor: Yeltsin Arrestegui
 * @creado: 2 mar. 2024 23:06:33
 */
package com.agencia.reservas.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * clase que reprenseta a un detella de un Lugar Turístico
 */
@Entity
@Data
public class LugarTurisitico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Validación para no permitir datos en blanco
	@NotBlank(message = "El nombre no puede estar en blanco")
	//Validación determinado número de caracteres
	@Size(max = 255, message = "El nombre debe tener como máximo 255 caracteres")
    private String nombre;
	
	//Validación para no permitir datos en blanco
    @NotBlank(message = "La descripción no puede estar en blanco")
	//Validación determinado número de caracteres
    @Size(max = 1000, message = "La descripción debe tener como máximo 1000 caracteres")
    private String descripcion;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "El tipo no puede estar en blanco")
	//Validación determinado número de caracteres
    @Size(max = 50, message = "El tipo debe tener como máximo 50 caracteres")
    private String tipo;

	//Validación para no permitir datos en blanco
    @NotNull(message = "El horario de apertura no puede estar en blanco")
    @Temporal(TemporalType.TIME)
    private Date horarioApertura;

	//Validación para no permitir datos en blanco
    @NotNull(message = "El horario de cierre no puede estar en blanco")
    @Temporal(TemporalType.TIME)
    private Date horarioCierre;

    /*
     * @ManyToOne indica una relación muchos a uno entre la clase LugarTurisitico y 
     * ella misma, lo que significa que un lugar turístico puede tener un lugar padre (otro lugar turístico)
     */
    @ManyToOne
    @JoinColumn(name = "lugar_padre_id") //especifica el nombre de la columna en la tabla LugarTurisitico que se utilizará para mapear esta relación. 
    @JsonBackReference //se usa para evitar la recursión infinita al serializar los objetos JSON, ya que esta relación es bidireccional.
    private LugarTurisitico lugarPadre;
    
    
    /*
     * @ManyToMany indica una relación muchos a muchos entre la clase LugarTurisitico y la clase Reserva. Esto significa que un
     * lugar turístico puede estar asociado con múltiples reservas, y una reserva puede estar asociada con múltiples lugares turísticos
     */
    @ManyToMany(mappedBy = ("lugaresTuristicos"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Reserva> reservas = new ArrayList<Reserva>();
}
