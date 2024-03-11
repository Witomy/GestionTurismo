/**
 * @file: ApiVersionConfig.java
 * @autor: Jheferson Chalan
 * @creado: 5 mar. 2024 07:04:34
 */
package com.agencia.reservas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agencia.reservas.util.ApiVersionInterceptor;

/**
 * 
 */
@Configuration
public class ApiVersionConfig {
	@Bean
    public ApiVersionInterceptor apiVersionInterceptor() {
        return new ApiVersionInterceptor("1.0");
    }

}
