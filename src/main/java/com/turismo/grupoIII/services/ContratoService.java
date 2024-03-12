/**
 * @file: ContratoService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:28:43
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
public interface ContratoService {
    List<Contrato> listarContratos();
    Optional<Contrato> buscarContratoPorId(Long idContrato) throws EntityNotFoundException;
    Contrato guardarContrato(Contrato contrato) throws IlegalOperationException, ConflictException;
    void eliminarContrato(Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    Contrato actualizarContrato(Long idContrato, Contrato contrato) throws EntityNotFoundException, IlegalOperationException, ConflictException;;
    Contrato asignarEmpleado(Long idEmpleado, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    Contrato asignarCliente(Long idCliente, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    public Contrato asignarRutaTuristica(Long idRutaTuristica, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    public List<RutaTuristica> listarRutasContrato(Long idContrato);
}


