/**
 * @file: GlobalExceptionHandler.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 23:18:16
 */
package com.turismo.grupoIII.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

/**
 * Clase que maneja globalmente las excepciones en la aplicación.
 * Proporciona métodos para manejar diferentes tipos de excepciones y generar respuestas adecuadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Maneja la excepción EntityNotFoundException.
     * @param ex Excepción EntityNotFoundException.
     * @param request La solicitud web.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> responseNotFoundException(EntityNotFoundException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	
	/**
     * Maneja la excepción Exception.
     * @param ex Excepción Exception.
     * @param request La solicitud web.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHundler(Exception ex, WebRequest request){
	    ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 /**
     * Maneja la excepción IlegalOperationException.
     * @param ex Excepción IlegalOperationException.
     * @param request La solicitud web.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(IlegalOperationException.class)
	public ResponseEntity<ErrorMessage> responseIllegalResponseEntity(IlegalOperationException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	/**
     * Maneja la excepción ConflictException.
     * @param ex Excepción ConflictException.
     * @param request La solicitud web.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorMessage> globalExceptionHundler(ConflictException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
     * Maneja la excepción MethodArgumentNotValidException.
     * @param ex Excepción MethodArgumentNotValidException.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "Error:";
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, message, "Error:");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

	/**
     * Maneja la excepción BindException.
     * @param ex Excepción BindException.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessage> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "Error de validación";
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, message, "Error:");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Maneja la excepción TransactionSystemException.
     * @param ex Excepción TransactionSystemException.
     * @param request La solicitud web.
     * @return ResponseEntity con el mensaje de error y el código de estado HTTP correspondiente.
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorMessage> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        String errorMessage = "Error en la transacción JPA: " + (ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    


}