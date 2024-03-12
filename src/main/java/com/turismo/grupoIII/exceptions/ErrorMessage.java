/**
 * @file: ErrorMassage.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:16:15
 */
package com.turismo.grupoIII.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa un mensaje de error que se puede devolver como respuesta a una solicitud HTTP.
 * Esta clase encapsula los detalles del error, como el código de estado, la marca de tiempo, el mensaje y la descripción.
 */
@Getter
@Setter
public class ErrorMessage {
	private int statusCode;
	private LocalDateTime timestap;
	private String message;
	private String descripcion;
	
	
	 /**
     * Crea una nueva instancia de ErrorMessage con los detalles especificados.
     * @param statusCode Código de estado HTTP del error.
     * @param message Mensaje descriptivo del error.
     * @param description Descripción detallada del error.
     */
	public ErrorMessage(HttpStatus statusCode, String messge, String descripcion) {
		// TODO Auto-generated constructor stub
		this.statusCode = statusCode.value();
		this.timestap = LocalDateTime.now();
		this.message = messge;
		this.descripcion = descripcion;
	}
}
