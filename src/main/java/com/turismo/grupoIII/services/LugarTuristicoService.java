/**
 * @file: LugarTuristicoService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:02:52
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
public interface LugarTuristicoService {
    List<LugarTuristico> listarLugaresTuristicos();
    Optional<LugarTuristico> buscarLugarTuristicoPorId(Long idLugarTuristico) throws EntityNotFoundException;
    LugarTuristico guardarLugarTuristico(LugarTuristico lugarTuristico) throws IlegalOperationException, ConflictException;
    void eliminarLugarTuristico(Long idLugarTuristico) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    LugarTuristico actualizarLugarTuristico(Long idLugarTuristico, LugarTuristico lugarTuristico) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    LugarTuristico asignarLugar(Long idDetalle, Long idLugar) throws EntityNotFoundException, ConflictException, IlegalOperationException;
    
}
