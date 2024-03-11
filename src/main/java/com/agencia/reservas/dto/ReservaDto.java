/**
 * @file: ReservaDto.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 21:48:33
 */
package com.agencia.reservas.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agencia.reservas.domain.Cliente;
import com.agencia.reservas.domain.DetalleReserva;
import com.agencia.reservas.domain.LugarTurisitico;
import com.agencia.reservas.domain.Pago;

import lombok.Data;

/**
 * 
 */
@Data
public class ReservaDto {
	
	private Long id;
    private Date fechaReserva;
    private String estado;
    private int totalPersonas;
    private Cliente cliente;
    private List<Pago> pagos = new ArrayList<Pago>();
    private DetalleReserva detalleReserva;
    private List<LugarTurisitico> lugaresTuristicos = new ArrayList<>();
}
