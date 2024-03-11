/**
 * @file: WebConfig.java
 * @autor: Jheferson Chalan
 * @creado: 5 mar. 2024 07:03:12
 */
package com.agencia.reservas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.agencia.reservas.util.ApiVersionInterceptor;

/**
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
    private ApiVersionInterceptor apiVersionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiVersionInterceptor);
    }

}
