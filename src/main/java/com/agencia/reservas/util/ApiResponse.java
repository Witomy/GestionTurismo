/**
 * @file: ApiResponse.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 23:20:40
 */
package com.agencia.reservas.util;

import lombok.Data;

/**
 * 
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
