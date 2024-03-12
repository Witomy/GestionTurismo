/**
 * @file: AuthService.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:25:47
 */
package com.turismo.grupoIII.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.turismo.grupoIII.Jwt.JwtService;
import com.turismo.grupoIII.domain.Role;
import com.turismo.grupoIII.domain.User;
import com.turismo.grupoIII.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Proporciona servicios de autenticación para la aplicación.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * Realiza la autenticación de un usuario utilizando el nombre de usuario y la contraseña proporcionados.
     *
     * @param request La solicitud de inicio de sesión que contiene las credenciales del usuario.
     * @return La respuesta de autenticación que incluye el token generado.
     * @throws Exception Si la autenticación falla.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }
    
    /**
     * Registra un nuevo usuario en la aplicación.
     *
     * @param request La solicitud de registro que contiene la información del nuevo usuario.
     * @return La respuesta de autenticación que incluye el token generado para el nuevo usuario registrado.
     */
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.lastname)
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

}
