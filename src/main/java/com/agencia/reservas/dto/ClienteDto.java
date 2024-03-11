/**
 * @file: ClienteDto.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 21:43:23
 */
package com.agencia.reservas.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agencia.reservas.domain.Reserva;

import lombok.Data;


/**
 * 
 */
@Data
public class ClienteDto {
	private Long id;
	private String dni;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String direccion;
    private Date fechaNacimiento;
    private String genero;
    private List<Reserva> reservas = new ArrayList<>();
}
