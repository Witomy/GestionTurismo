/**
 * @file: ConflictException.java
 * @autor: Mondragón Hernandez
 * @creado: 4 mar. 2024 07:34:48
 */
package com.agencia.reservas.exceptions;

/**
 * la clase ConflictException es una excepción personalizada que se utiliza 
 * para indicar un conflicto en la aplicación. Se utiliza para lanzar una 
 * excepción cuando ocurre un conflicto específico, y se proporciona un 
 * mensaje de error para informar sobre la naturaleza del conflicto.
 */
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Toma un parámetro message que representa el mensaje de error
	 * asociado con la excepción. Llama al constructor de la 
	 * superclase RuntimeException y pasa el mensaje como argumento
	 */
	public ConflictException(String message) {
		// TODO Auto-generated constructor stub
		super(message);	
	}

}
