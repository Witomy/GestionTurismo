/**
 * @file: LugarTuristicoDto.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 21:46:30
 */
package com.agencia.reservas.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agencia.reservas.domain.LugarTurisitico;
import com.agencia.reservas.domain.Reserva;

import lombok.Data;
/**
 * 
 */
@Data
public class LugarTuristicoDto {
	private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Date horarioApertura;
    private Date horarioCierre;
    private LugarTurisitico lugarPadre;
   private List<Reserva> reservas = new ArrayList<Reserva>();
}
