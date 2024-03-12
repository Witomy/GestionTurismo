/**
 * @file: RegisterRequest.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:23:17
 */
package com.turismo.grupoIII.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una solicitud de registro enviada por el cliente.
 */
@Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 public class RegisterRequest {
     
	/** El nombre de usuario deseado para el nuevo usuario. */
     String username;
     
     /** La contraseña deseada para el nuevo usuario. */
     String password;
     
     /** El nombre del nuevo usuario. */
     String firstname;
     
     /** El apellido del nuevo usuario. */
     String lastname;
     
     /** El país de residencia del nuevo usuario. */
     String country; 
 }
