/**
 * @file: LugarTurisiticoService.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:37:31
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import com.agencia.reservas.domain.LugarTurisitico;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;


/**
 * 
 */
public interface LugarTurisiticoService {
	List<LugarTurisitico> listarLugaresTuristicos();
    Optional<LugarTurisitico> buscarLugarTuristicoPorId(Long id)throws EntityNotFoundException;
    LugarTurisitico guardarLugarTuristico(LugarTurisitico lugarTuristico)throws IllegalOperationException;
    void eliminarLugarTuristico(Long id)throws EntityNotFoundException, IllegalOperationException;
}
