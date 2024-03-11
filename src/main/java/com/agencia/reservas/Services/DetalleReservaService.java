/**
 * @file: DetalleReservaService.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:36:18
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import com.agencia.reservas.domain.DetalleReserva;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;

/**
 * Se crean los métodos que se implementarán luego y se usaran en lo controllers. 
 * se añaden las exceptions
 */
public interface DetalleReservaService {
	List<DetalleReserva> listarDetallesReserva();
    Optional<DetalleReserva> buscarDetallesReservaPorId(Long id) throws EntityNotFoundException;
    DetalleReserva guardarDetallesReserva(DetalleReserva detallesReserva);
    void eliminarDetallesReserva(Long id)throws EntityNotFoundException, IllegalOperationException;
}
