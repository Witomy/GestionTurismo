/**
 * @file: ClienteService.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:30:15
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import com.agencia.reservas.domain.Cliente;
import com.agencia.reservas.exceptions.ConflictException;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;


/**
 * Se crean los métodos que se implementarán luego y se usaran en lo controllers. 
 * se añaden las exceptions
 */
public interface ClienteService {
	List<Cliente> listarClientes();
    Optional<Cliente> buscarClientePorId(Long id) throws EntityNotFoundException;
    Cliente guardarCliente(Cliente cliente)throws IllegalOperationException, ConflictException;
    void eliminarCliente(Long id) throws EntityNotFoundException, IllegalOperationException, ConflictException;
    public boolean existePorCorreo(String correo);
}
	