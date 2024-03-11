/**
 * @file: AplicationConfig.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 23:36:09
 */
package com.agencia.reservas.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
public class AplicationConfig {
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
