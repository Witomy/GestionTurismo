/**
 * @file: IllegalOperationException.java
 * @autor: Mondragón Hernandez
 * @creado: 5 mar. 2024 00:20:22
 */
package com.agencia.reservas.exceptions;

/**
 * es una excepción personalizada que se utiliza para indicar que se ha
 * producido una operación ilegal o no válida en la aplicación. Se utiliza
 * para lanzar una excepción cuando se detecta una operación que no debería
 * haber ocurrido en ciertas circunstancias, y se proporciona un mensaje de
 * error para informar sobre la naturaleza de la operación ilegal.
 */

public class IllegalOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IllegalOperationException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
