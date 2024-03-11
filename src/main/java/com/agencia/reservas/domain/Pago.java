/**
 * @file: Pago.java
 * @autor: Yeltsin Arrestegui
 * @creado: 2 mar. 2024 23:03:47
 */
package com.agencia.reservas.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Clase que representa a Pago
 */
@Entity
@Data
public class Pago {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	//Validación que no permite que el monto sea menor a 0
	@Positive(message = "El monto debe ser un número positivo")
    private Double monto;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "El método de pago no puede estar en blanco")
    private String metodoPago;

	//Validación para no permitir datos en blanco
    @NotNull(message = "La fecha de pago no puede estar en blanco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    
	//Validación para no permitir datos en blanco
    @NotBlank(message = "El estado del pago no puede estar en blanco")
    @Pattern(regexp = "^(Pendiente|Pagado|Cancelado)$", message = "El estado del pago debe ser Pendiente, Pagado o Cancelado")
    private String estadoPago;
    
    /*
     * @ManyToOne) indica una relación muchos a uno entre la clase Pago y la clase Reserva
     * Ya que un pago está asociado con una única reserva. 
     */
    @ManyToOne
    @JoinColumn(name = "idReserva") //especifica el nombre de la columna en la tabla Pago que se utilizará para mapear esta relación. 
    //@JsonBackReference
    private Reserva reserva;
}
