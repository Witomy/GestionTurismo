/**
 * @file: GlobalExceptionHandler.java
 * @autor: Mondragón Hernandez
 * @creado: 4 mar. 2024 08:16:21
 */
package com.agencia.reservas.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

/**
 * este controlador de excepciones proporciona manejo personalizado para diferentes tipos de excepciones, 
 * generando respuestas apropiadas con mensajes de error específicos y códigos de estado HTTP correspondientes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	
	/*
	 * @ExceptionHandler(EntityNotFoundException.class): Este método maneja las excepciones del tipo EntityNotFoundException. 
	 * Toma un objeto EntityNotFoundException y un objeto WebRequest como parámetros. Crea un objeto ErrorMessage con el
	 * código de estado NOT_FOUND (404), el mensaje de la excepción y una descripción generada a partir del WebRequest.
	 * Luego, devuelve una respuesta ResponseEntity con el objeto ErrorMessage y el código de estado correspondiente	
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> responseNotFoundException(EntityNotFoundException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	
	/*
	 * Este método maneja las excepciones del tipo ConflictException. 
	 * Funciona de manera similar al método anterior, pero utiliza un código de estado CONFLICT (409).
	 */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorMessage> responseConflictException(ConflictException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.CONFLICT);
	}
	
	
	/*
	 * Este método maneja las excepciones del tipo IllegalOperationException. 
	 * Al igual que los métodos anteriores, crea un objeto ErrorMessage con el código de estado BAD_REQUEST (400).
	 */
	@ExceptionHandler(IllegalOperationException.class)
	public ResponseEntity<ErrorMessage> responseIllegalResponseEntity(ConflictException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	
	/*
	 *  Este método maneja todas las demás excepciones que no están cubiertas por los métodos anteriores. 
	 *  Crea un objeto ErrorMessage con el código de estado INTERNAL_SERVER_ERROR (500) y el mensaje de error de la excepción.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHundler(ConflictException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	/*
	 * Este método maneja las excepciones MethodArgumentNotValidException, que ocurren cuando falla la validación de 
	 * los argumentos de un método anotado con @Valid. Este método recupera los errores de validación del BindingResult
	 * y los coloca en un mapa para ser devueltos como parte de la respuesta.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
	
	
	/*
	 *  Este método maneja las excepciones ConstraintViolationException, que ocurren cuando se violan las
	 *  restricciones de validación de los objetos validados con la anotación @Validated. Recupera las
	 *  violaciones de restricción y las coloca en un mapa para ser devueltas como parte de la respuesta.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            errores.put(violation.getPropertyPath().toString(), "Error de validación: " + violation.getMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
