/**
 * @file: ReservaService.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:39:38
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import com.agencia.reservas.domain.Reserva;
import com.agencia.reservas.exceptions.ConflictException;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;


/**
 * Se crean los métodos que se implementarán luego y se usaran en lo controllers. 
 * se añaden las exceptions
 */
public interface ReservaService {
	List<Reserva> listarReservas();
    Optional<Reserva> buscarReservaPorId(Long id) throws EntityNotFoundException;
    Reserva guardarReserva(Reserva reserva) throws IllegalOperationException;
    void eliminarReserva(Long id)throws EntityNotFoundException, IllegalOperationException;
    Reserva asignarReserva(Long idCliente, Long idReserva)throws EntityNotFoundException, IllegalOperationException, ConflictException;
}
