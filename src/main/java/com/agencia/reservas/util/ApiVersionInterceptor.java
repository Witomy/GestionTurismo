/**
 * @file: ApiVersionInterceptor.java
 * @autor: Jheferson Chalan
 * @creado: 5 mar. 2024 07:01:56
 */
package com.agencia.reservas.util;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 */
public class ApiVersionInterceptor implements HandlerInterceptor {
	private static final String HEADER_API_VERSION = "Api-Version";
    private String version;

    public ApiVersionInterceptor(String version) {
        this.version = version;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader(HEADER_API_VERSION, version);
        return true;
    }

}
