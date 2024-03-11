/**
 * @file: PadoDto.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 21:47:25
 */
package com.agencia.reservas.dto;

import java.util.Date;

import com.agencia.reservas.domain.Reserva;
import lombok.Data;

/**
 * 
 */
@Data
public class PagoDto {
	
	private Long id;
    private Double monto;
    private String metodoPago;
    private Date fechaPago;
    private String estadoPago;
    private Reserva reserva;
}
