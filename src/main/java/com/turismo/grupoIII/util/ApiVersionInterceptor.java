/**
 * @file: ApiVersionInterceptor.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 12 mar. 2024 03:06:46
 */
package com.turismo.grupoIII.util;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
public class ApiVersionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lógica para manejar la versión de la API
        String apiVersionHeader = request.getHeader("Api-Version");

        // Puedes agregar lógica adicional según tus necesidades, como validar la versión o lanzar excepciones si es necesario.

        return true;
    }

    // Puedes implementar otros métodos de la interfaz HandlerInterceptor según tus necesidades
}