/**
 * @file: LoginRequest.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:23:56
 */
package com.turismo.grupoIII.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una solicitud de inicio de sesión enviada por el cliente.
 */
 @Data
 @Builder
 @AllArgsConstructor
 @NoArgsConstructor
 public class LoginRequest {
	 
    /** El nombre de usuario proporcionado para iniciar sesión. */
     String username;
     /** La contraseña proporcionada para iniciar sesión. */
     String password; 
 }

