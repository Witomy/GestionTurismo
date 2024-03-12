/**
 * @file: EntityNotFoundException.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:10:02
 */
package com.turismo.grupoIII.exceptions;

/**
 * Excepción lanzada cuando no se encuentra una entidad en la aplicación.
 * Esta excepción se utiliza para indicar que no se puede encontrar una entidad específica en la base de datos u otro repositorio de datos.
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
     * Crea una nueva instancia de EntityNotFoundException con el mensaje especificado.
     * @param message Mensaje descriptivo de la excepción.
     */
	public EntityNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);	
	}
}
