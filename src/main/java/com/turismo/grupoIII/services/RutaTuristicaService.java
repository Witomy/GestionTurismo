/**
 * @file: RutaTuristicaService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:02:37
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
//RutaTuristicaService.java
public interface RutaTuristicaService {
 List<RutaTuristica> listarRutasTuristicas();
 Optional<RutaTuristica> buscarRutaTuristicaPorId(Long idRutaTuristica) throws EntityNotFoundException;
 RutaTuristica guardarRutaTuristica(RutaTuristica rutaTuristica) throws IlegalOperationException, ConflictException;
 void eliminarRutaTuristica(Long idRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException;
 RutaTuristica actualizarRutaTuristica(Long idRutaTuristica, RutaTuristica rutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException;
 List <Contrato> listarContratosRuta(Long idRuta);
}