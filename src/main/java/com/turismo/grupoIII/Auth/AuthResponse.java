/**
 * @file: AuthResponse.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:24:50
 */
package com.turismo.grupoIII.Auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la respuesta de autenticación enviada al cliente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    /** El token de autenticación generado para el usuario autenticado. */
    String token; 
}

