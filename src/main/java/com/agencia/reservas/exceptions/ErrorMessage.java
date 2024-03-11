/**
 * @file: ErrorMessage.java
 * @autor: Mondragón Hernandez
 * @creado: 4 mar. 2024 08:07:52
 */
package com.agencia.reservas.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * se utiliza para representar un mensaje de error personalizado en la aplicación,
 * incluyendo el código de estado HTTP, la fecha y hora del mensaje, el mensaje
 * de error y una descripción más detallada del error.
 */
@Getter
@Setter
public class ErrorMessage {
	private int statusCode;
	private LocalDateTime timestap;
	private String message;
	private String descripcion;
	
	
	/**
	 * Toma un parámetro "message" que representa el mensaje de error asociado con la excepción.
	 * y un parametro descripción y Llama al constructor
	 */
	public ErrorMessage(HttpStatus statusCode, String messge, String descripcion) {
		// TODO Auto-generated constructor stub
		this.statusCode = statusCode.value();
		this.timestap = LocalDateTime.now();
		this.message = messge;
		this.descripcion = descripcion;
	}
}
