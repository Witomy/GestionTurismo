/**
 * @file: Reserva.java
 * @autor: Yeltsin Arrestegui
 * @creado: 2 mar. 2024 22:23:27
 */
package com.agencia.reservas.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * clase que represneta a una reserva
 */
@Entity
@Data
public class Reserva {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	//	//Validación para no permitir datos en blanco
	@NotNull(message = "La fecha de reserva no puede estar en blanco")
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;

	//	//Validación para no permitir datos en blanco
    @NotBlank(message = "El estado de la reserva no puede estar en blanco")
    //Validación que hace que el estado sea sólo de 3 tipos
    @Pattern(regexp = "^(Pendiente|Confirmada|Cancelada)$", message = "El estado de la reserva debe ser Pendiente, Confirmada o Cancelada")
    private String estado;

    //Validación que no permite que el número sea negativo
    @Positive(message = "El número total de personas debe ser un número positivo")
    private int totalPersonas;
    
    
    /*
     * @ManyToOne) indica una relación muchos a uno entre la clase Reserva y la clase Cliente
     * esto significa que una reserva está asociada con un único cliente. 
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    //@JsonBackReference
    private Cliente cliente;
    
    
    /*
     * @OneToMany) indica una relación uno a muchos entre la clase Reserva y la clase Pago, 
     * Esto significa que una reserva puede estar asociada con múltiples pagos.
     */
    @OneToMany(mappedBy = "reserva")
    //@JsonManagedReference
    private List<Pago> pagos = new ArrayList<Pago>();
    
    
    
    /*
     * @OneToOne) indica una relación uno a uno entre la clase Reserva y la clase DetalleReserva 
     * Esto significa que una reserva está asociada con un único detalle de reserva.
     */
    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private DetalleReserva detalleReserva;
    
    
    /*
     * @ManyToMany) indica una relación muchos a muchos entre la clase Reserva y la clase LugarTurisitico, 
     * lo que significa que una reserva puede estar asociada con múltiples lugares turísticos y 
     * un lugar turístico puede estar asociado con múltiples reservas.
     */
    @ManyToMany
    /*
     *  se utiliza para configurar una tabla de unión para una relación muchos a muchos entre
     *  las entidades Reserva y LugarTuristico, especificando el nombre de la tabla de unión y
     *  las columnas que se utilizarán para mapear la relación con cada entidad
     */
    @JoinTable(
        name = "reserva_lugar_turistico", 
        joinColumns = @JoinColumn(name = "reserva_id"), 
        inverseJoinColumns = @JoinColumn(name = "lugar_turistico_id")) 
    //@JsonManagedReference
    private List<LugarTurisitico> lugaresTuristicos = new ArrayList<>();
}
