/**
 * @file: DetalleRutaTuristicaService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:02:24
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
public interface DetalleRutaTuristicaService {
    List<DetalleRutaTuristica> listarDetallesRutaTuristica();
    Optional<DetalleRutaTuristica> buscarDetalleRutaTuristicaPorId(Long idDetalleRutaTuristica) throws EntityNotFoundException;
    DetalleRutaTuristica guardarDetalleRutaTuristica(DetalleRutaTuristica detalleRutaTuristica) throws IlegalOperationException, ConflictException;
    void eliminarDetalleRutaTuristica(Long idDetalleRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    public DetalleRutaTuristica actualizarDetalleRutaTuristica(Long idDetalleRuta, DetalleRutaTuristica detalleRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException ;
    public List <LugarTuristico> listarLugaresTuristicos(Long idRuta);
}

