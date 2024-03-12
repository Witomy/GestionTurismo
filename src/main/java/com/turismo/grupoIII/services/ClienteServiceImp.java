/**
 * @file: ClienteServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:30:59
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.ClienteRepositoryJPA;



/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Service
public class ClienteServiceImp implements ClienteService{

	@Autowired
    private ClienteRepositoryJPA clienteRepository;
	
	
	/**
     * Obtiene una lista de todos los clientes.
     * @return Lista de clientes.
     */
    @Override
    @Transactional
    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     * @param idCliente ID del cliente a buscar.
     * @return Cliente encontrado, si existe.
     * @throws EntityNotFoundException Si el cliente no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarClientePorId(Long idCliente) throws EntityNotFoundException{
    	Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
        return cliente;
    }

    /**
     * Guarda un cliente.
     * @param cliente Cliente a guardar.
     * @return Cliente guardado.
     * @throws IlegalOperationException Si la operación no es válida.
     * @throws ConflictException Si hay un conflicto.
     */
    @Override
    @Transactional
    public Cliente guardarCliente(Cliente cliente)throws IlegalOperationException, ConflictException {
    	if (cliente.getIdCliente() != null && clienteRepository.existsById(cliente.getIdCliente())) {
            throw new ConflictException("Ya existe un cliente con el mismo ID");
        }
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente por su ID.
     * @param idCliente ID del cliente a eliminar.
     * @throws EntityNotFoundException Si el cliente no se encuentra.
     * @throws IlegalOperationException Si la operación no es válida.
     * @throws ConflictException Si hay un conflicto.
     */
    @Override
    @Transactional
    public void eliminarCliente(Long idCliente) throws EntityNotFoundException, IlegalOperationException, ConflictException{
    	Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
        clienteRepository.deleteById(idCliente);
    }
    
    /**
     * Verifica si existe un cliente con el DNI especificado.
     * @param dni DNI del cliente a verificar.
     * @return true si existe un cliente con el DNI especificado, false de lo contrario.
     */
    @Override
	@Transactional
    public boolean existePordni(String dni) {
        return clienteRepository.existsBydni(dni);
    }
    	
    /**
     * Actualiza un cliente.
     * @param id ID del cliente a actualizar.
     * @param cliente Cliente con los datos actualizados.
     * @return Cliente actualizado.
     * @throws EntityNotFoundException Si el cliente no se encuentra.
     * @throws IlegalOperationException Si la operación no es válida.
     * @throws ConflictException Si hay un conflicto.
     */
    @Override
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente cliente) throws EntityNotFoundException, IlegalOperationException, ConflictException {
    	Optional<Cliente> clienteEntity = clienteRepository.findById(id);
		if(clienteEntity.isEmpty()) {
			throw new EntityNotFoundException("Cliente no encontrado");
		}
			 
		if(!clienteRepository.findBydni(cliente.getDni()).isEmpty()) {
			throw new IlegalOperationException("El dni del cliente ya existe");
		}	
		cliente.setIdCliente(id);		
		return clienteRepository.save(cliente);
    }
    
    /**
     * Lista todos los contratos asociados a un cliente.
     * @param idCliente ID del cliente.
     * @return Lista de contratos asociados al cliente.
     */
    @Override
	@Transactional
	public List<Contrato> listarContratosCliente(Long idCliente) {
		// TODO Auto-generated method stub
		return (List <Contrato>)clienteRepository.findById(idCliente).get().getContratos();
	}
    

}