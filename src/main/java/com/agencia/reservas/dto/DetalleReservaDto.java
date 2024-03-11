/**
 * @file: DetalleReservaDto.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 21:45:42
 */
package com.agencia.reservas.dto;

import com.agencia.reservas.domain.Reserva;

import lombok.Data;

/**
 * 
 */
@Data
public class DetalleReservaDto {
	private Long id;
    private String informacionAdicional;
    private Reserva reserva;
}
