/**
 * @file: DetalleReserva.java
 * @autor: Yeltsin Arrestegui
 * @creado: 3 mar. 2024 08:02:51
 */
package com.agencia.reservas.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * clase que reprenseta a un detella de una reserva
 */
@Entity//importa los métodos usando Lombok
@Data//señala que es de tipo entidad
public class DetalleReserva {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	//Validación para no permitir datos en blanco
	@NotBlank(message = "La información adicional no puede estar en blanco")
	//Validación para no permitir más de una cantidad determinada de caracteres
    @Size(max = 255, message = "La información adicional debe tener como máximo 255 caracteres")
    private String informacionAdicional;
    

    //@OneToOne: Relación de uno a uno entre la entidad DetalleReserva y Reserva,
    //ya que un detalle de reserva está asociado con una única reserva. 
    @OneToOne
    //Especifica el nombre de la columna en la tabla DetalleReserva que se utilizará para mapear esta relación.
    @JoinColumn(name = "idReserva")
    //@JsonManagedReference
    private Reserva reserva;
}
