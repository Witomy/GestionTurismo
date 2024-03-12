/**
 * @file: WebConfig.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 12 mar. 2024 03:07:28
 */
package com.turismo.grupoIII.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.turismo.grupoIII.util.ApiVersionInterceptor;

/**
 * Configuración de la aplicación relacionada con la capa web.
 * Esta clase se utiliza para configurar interceptores que pueden procesar solicitudes antes y después de que se manejen en los controladores.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	
    /**
     * Agrega un interceptor para procesar solicitudes HTTP.
     *
     * @param registry El registro de interceptores.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiVersionInterceptor());
    }

    // Otros métodos de configuración pueden ir aquí
}