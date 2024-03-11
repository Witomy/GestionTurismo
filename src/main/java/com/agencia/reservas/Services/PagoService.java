/**
 * @file: PagoService.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:38:37
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import com.agencia.reservas.domain.Pago;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;


/**
 * Se crean los métodos que se implementarán luego y se usaran en lo controllers. 
 * se añaden las exceptions
 */
public interface PagoService {
	List<Pago> listarPagos();
    Optional<Pago> buscarPagoPorId(Long id)throws EntityNotFoundException;
    Pago guardarPago(Pago pago)throws IllegalOperationException;
    void eliminarPago(Long id)throws EntityNotFoundException, IllegalOperationException;
}
