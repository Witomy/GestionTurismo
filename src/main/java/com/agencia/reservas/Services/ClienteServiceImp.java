/**
 * @file: ClienteServiceImp.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 15:59:10
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agencia.reservas.domain.Cliente;
import com.agencia.reservas.exceptions.ConflictException;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;
import com.agencia.reservas.repository.ClienteRepositoryJpa;

/**
 * Se implementan los métodos que luego se usaran en lo controllers. 
 * se añaden las exceptions
 */
@Service
public class ClienteServiceImp implements ClienteService{

	@Autowired
    private ClienteRepositoryJpa clienteRepository;
	
	

    @Override
    @Transactional
    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarClientePorId(Long idCliente) throws EntityNotFoundException{
    	Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
        return cliente;
    }

    @Override
    @Transactional
    public Cliente guardarCliente(Cliente cliente)throws IllegalOperationException, ConflictException {
    	if (cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
    		//mensaje de rror para cuando el cliente ya esté creado y se tenga el msmo ID
            throw new ConflictException("Ya existe un cliente con el mismo ID");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long idCliente) throws EntityNotFoundException, IllegalOperationException, ConflictException{
    	Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
        clienteRepository.deleteById(idCliente);
    }
    public boolean existePorCorreo(String correo) {
        return clienteRepository.existsByCorreo(correo);
    }


}
