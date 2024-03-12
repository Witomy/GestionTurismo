/**
 * @file: TransactionSystemException.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 10 mar. 2024 17:58:12
 */
package com.turismo.grupoIII.exceptions;

/**
 * Excepción lanzada cuando se produce un error en el sistema de transacciones.
 * Esta clase representa una excepción de tiempo de ejecución.
 */
public class TransactionSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	 /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error que describe la excepción.
     */
	public TransactionSystemException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
