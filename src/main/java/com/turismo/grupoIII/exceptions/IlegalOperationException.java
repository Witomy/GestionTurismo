/**
 * @file: IlegalOperationException.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:18:41
 */
package com.turismo.grupoIII.exceptions;

/**
 * Excepción lanzada cuando se produce una operación ilegal o no permitida.
 * Esta clase representa una excepción de tiempo de ejecución.
 */
public class IlegalOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	 /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error que describe la excepción.
     */
	public IlegalOperationException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
