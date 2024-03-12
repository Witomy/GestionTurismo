/**
 * @file: ClienteRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 00:22:07
 */
package com.turismo.grupoIII.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.dto.ClienteDTO;
import com.turismo.grupoIII.dto.ContratoDTO;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.services.ClienteService;
import com.turismo.grupoIII.services.ContratoService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los clientes.
 */
@RestController
@RequestMapping(value ="/api/clientes" , headers = "Api-Version=1")
	public class ClienteRest {
		
	 /**
     * Servicio para manejar operaciones relacionadas con los clientes.
     */
	@Autowired
    private ClienteService clienteServ;

	 /**
     * Mapeador para convertir entidades a DTOs y viceversa.
     */
    @Autowired
    private ModelMapper modelMapper;

   
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos(){
        List<Cliente> clientes = clienteServ.listarClientes();
        List<ClienteDTO> clientesDtos = clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).collect(Collectors.toList());
        // Agregar enlaces HATEOAS a cada cliente en la lista
        clientesDtos.forEach(clienteDTO -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(ClienteRest.class).slash(clienteDTO.getIdCliente()).withSelfRel();
            clienteDTO.add(selfLink);
        });
        ApiResponse<List<ClienteDTO>> response = new ApiResponse<>(true, "Lista de clientes obtenida con éxito", clientesDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un cliente específico por su identificador único.
     * @param id Identificador único del cliente.
     * @return ResponseEntity con ApiResponse que contiene el cliente obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteServ.buscarClientePorId(id);
        ClienteDTO clienteDto = modelMapper.map(cliente, ClienteDTO.class);
        
        Link contratosLink = WebMvcLinkBuilder.linkTo(ClienteRest.class).slash(id).slash("contratos").withRel("contratos");
        clienteDto.add(contratosLink);
        ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente obtenido con éxito", clienteDto);
        return ResponseEntity.ok(response);
    }

    
    /**
     * Obtiene todos los contratos asociados a un cliente específico.
     * @param id Identificador único del cliente.
     * @return ResponseEntity con ApiResponse que contiene la lista de contratos del cliente.
     */
    @GetMapping("/{id}/contratos")
    public ResponseEntity<?> obtenerContratosCliente(@PathVariable Long id) {
        List<Contrato> contratos = clienteServ.listarContratosCliente(id);
        List<ContratoDTO> contratosDtos = contratos.stream().map(contrato -> modelMapper.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
        contratosDtos.forEach(contratoDTO -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(ClienteRest.class).slash(contratoDTO.getIdContrato()).withSelfRel();
            contratoDTO.add(selfLink);
        });
        ApiResponse<List<ContratoDTO>> response = new ApiResponse<>(true, "Mostrando contratos del cliente solicitado", contratosDtos);
        return ResponseEntity.ok(response);
    }
	
	
    /**
     * Guarda un nuevo cliente en el sistema.
     *
     * @param clienteDto La información del cliente a guardar.
     * @param result El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ClienteDTO clienteDto, BindingResult result){
		if (result.hasErrors()) {
            return validar(result);
        }
		if (!clienteDto.getDni().isEmpty() && clienteServ.existePordni(clienteDto.getCorreo())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "DNI registrado por otro cliente"));
        }
		Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
		clienteServ.guardarCliente(cliente);
		ClienteDTO savedCliente = modelMapper.map(cliente, ClienteDTO.class);
		ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente guardado con éxito", savedCliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * Elimina un cliente del sistema por su ID.
	 *
	 * @param id El ID del cliente a eliminar.
	 * @return ResponseEntity con el resultado de la operación y detalles adicionales.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		clienteServ.eliminarCliente(id);
		ApiResponse<String> response = new ApiResponse<String>(true, "Cliente eliminado con éxito", null);
		return ResponseEntity.ok(response);
	}	

	
	/**
	 * Actualiza la información de un cliente existente en el sistema.
	 *
	 * @param id El ID del cliente a actualizar.
	 * @param clienteDTO La información actualizada del cliente.
	 * @param result El resultado de la validación de entrada.
	 * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ClienteDTO>> actualizarCliente(@Valid @PathVariable Long id, @RequestBody Cliente clienteDTO, BindingResult result) {
	    if (result.hasErrors()) {
	        return ResponseEntity.badRequest().body(
	                new ApiResponse<>(false, "Error de validación", null));
	    }

	    Cliente clienteActualizado = clienteServ.actualizarCliente(id, clienteDTO);
	    ClienteDTO clienteDtoActualizado = modelMapper.map(clienteActualizado, ClienteDTO.class);

	    ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente actualizado con éxito", clienteDtoActualizado);

	    return ResponseEntity.ok(response);
	}
	 
	
	/**
	 * Realiza la validación de los errores en la entrada y los devuelve como respuesta.
	 *
	 * @param result El resultado de la validación de entrada.
	 * @return ResponseEntity con los errores de validación.
	 */
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
	        Map<String, String> errores = new HashMap<>();
	        result.getFieldErrors().forEach(err -> {
	            errores.put(err.getField(), "Error en campo " + err.getField() + " " + err.getDefaultMessage());
	        });
	        return ResponseEntity.badRequest().body(errores);
	    }

}	

		
	