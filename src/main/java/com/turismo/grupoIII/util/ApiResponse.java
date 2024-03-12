/**
 * @file: ApiResponse.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 00:26:30
 */
package com.turismo.grupoIII.util;

import lombok.Data;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Data
public class ApiResponse<T> {
	private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
