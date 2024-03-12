/**
 * @file: ApplicationConfig.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:36:13
 */
package com.turismo.grupoIII.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.turismo.grupoIII.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Configuración de la aplicación para la gestión de autenticación y otros aspectos.
 */
@Configuration
@RequiredArgsConstructor
public class AplicationConfig {
	
	/**
     * Configura el bean ModelMapper para realizar mapeos de objetos.
     *
     * @return El bean ModelMapper configurado.
     */
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    private final UserRepository userRepository;

    /**
     * Configura el bean AuthenticationManager para manejar la autenticación de la aplicación.
     *
     * @param config La configuración de autenticación.
     * @return El bean AuthenticationManager configurado.
     * @throws Exception Si hay un error al configurar el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }


    /**
     * Configura el bean AuthenticationProvider para proporcionar la autenticación de la aplicación.
     *
     * @return El bean AuthenticationProvider configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    
    /**
     * Configura el bean PasswordEncoder para proporcionar la codificación de contraseñas.
     *
     * @return El bean PasswordEncoder configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el bean UserDetailsService para manejar los detalles de los usuarios.
     *
     * @return El bean UserDetailsService configurado.
     */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not fournd"));
    }
}
