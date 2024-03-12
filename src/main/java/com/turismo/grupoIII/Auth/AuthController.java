/**
 * @file: AuthController.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:26:51
 */
package com.turismo.grupoIII.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Controlador para la autenticación de usuarios.
 * Maneja las solicitudes relacionadas con la autenticación de usuarios,
 * como iniciar sesión y registrar nuevos usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Maneja las solicitudes de inicio de sesión.
     *
     * @param request La solicitud de inicio de sesión que contiene las credenciales del usuario.
     * @return ResponseEntity con la respuesta de la autenticación.
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    
    /**
     * Maneja las solicitudes de registro de nuevos usuarios.
     *
     * @param request La solicitud de registro que contiene la información del nuevo usuario.
     * @return ResponseEntity con la respuesta de la autenticación.
     */
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
