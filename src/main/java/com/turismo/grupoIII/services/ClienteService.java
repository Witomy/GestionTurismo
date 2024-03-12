/**
 * @file: ClienteService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:28:21
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Interfaz para el servicio relacionado con la entidad Cliente.
 */
public interface ClienteService {
	/**
     * Lista todos los clientes.
     * @return Lista de clientes.
     */
    List<Cliente> listarClientes();
    
    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente a buscar.
     * @return Optional que contiene el cliente encontrado, o vacío si no se encuentra.
     * @throws EntityNotFoundException Excepción lanzada si el cliente no se encuentra.
     */
    Optional<Cliente> buscarClientePorId(Long id) throws EntityNotFoundException;
    
    /**
     * Guarda un cliente.
     * @param cliente Cliente a guardar.
     * @return Cliente guardado.
     * @throws IlegalOperationException Excepción lanzada si la operación no es válida.
     * @throws ConflictException Excepción lanzada si hay un conflicto.
     */
    Cliente guardarCliente(Cliente cliente) throws IlegalOperationException, ConflictException;
    
    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar.
     * @throws EntityNotFoundException Excepción lanzada si el cliente no se encuentra.
     * @throws IlegalOperationException Excepción lanzada si la operación no es válida.
     * @throws ConflictException Excepción lanzada si hay un conflicto.
     */
    void eliminarCliente(Long id) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    
    /**
     * Verifica si existe un cliente con el DNI especificado.
     * @param dni DNI del cliente a verificar.
     * @return true si existe un cliente con el DNI especificado, false de lo contrario.
     */
    boolean existePordni(String dni);
    
    /**
     * Actualiza un cliente.
     * @param id ID del cliente a actualizar.
     * @param cliente Cliente con los datos actualizados.
     * @return Cliente actualizado.
     * @throws EntityNotFoundException Excepción lanzada si el cliente no se encuentra.
     * @throws IlegalOperationException Excepción lanzada si la operación no es válida.
     * @throws ConflictException Excepción lanzada si hay un conflicto.
     */
    Cliente actualizarCliente(Long id, Cliente cliente) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    
    /**
     * Lista todos los contratos asociados a un cliente.
     * @param idCliente ID del cliente.
     * @return Lista de contratos asociados al cliente.
     */
    List<Contrato> listarContratosCliente(Long idCliente);

}
