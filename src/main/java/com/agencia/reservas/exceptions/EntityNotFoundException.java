/**
 * @file: EntityNotFoundException.java
 * @autor: Mondragón Hernandez
 * @creado: 4 mar. 2024 01:51:15
 */
package com.agencia.reservas.exceptions;

/**
 * La clase EntityNotFoundException es una excepción personalizada que
 * se utiliza para indicar que no se ha encontrado una entidad específica en la aplicación
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Toma un parámetro "message" que representa el mensaje de error asociado con la excepción.
	 * Llama al constructor de la superclase RuntimeException y pasa el mensaje como argumento.
	 */
	public EntityNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);	
	}
}
