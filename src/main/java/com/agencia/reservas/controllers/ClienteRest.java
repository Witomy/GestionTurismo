/**
 * @file: ClienteController.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 16:59:53
 */
package com.agencia.reservas.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agencia.reservas.Services.ClienteService;
import com.agencia.reservas.domain.Cliente;
import com.agencia.reservas.dto.ClienteDto;
import com.agencia.reservas.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * clase que controla la base de datos cliente, usando Get, PUT, POS y Delete
 */
@RestController
@RequestMapping("api/cliente")
public class ClienteRest {
	
	@Autowired
	private ClienteService clienteServ;
	
	@Autowired
    private ModelMapper modelMapper;
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos los clientes.
	 */
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		//Se llama a un servicio llamado clienteServ para obtener una lista de todos los clientes almacenados en la base de datos.
		List<Cliente> clientes = clienteServ.listarClientes();
		List<ClienteDto> clientesDtos = clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDto.class)).collect(Collectors.toList());
		ApiResponse<List<ClienteDto>> response = new ApiResponse<>(true, "Lista de cllientes obtenida con éxito", clientesDtos);
		return ResponseEntity.ok(response);
	}
	
	
	/*llama a la lista de clientes mediante haciendo uso del id
	 * se llama a un servicio llamado clienteServ para buscar un cliente por su ID
	 * luego se almacena en un optional
	 */
	@GetMapping("/id")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteServ.buscarClientePorId(id);
		ClienteDto clienteDto = modelMapper.map(cliente, ClienteDto.class);
		ApiResponse<ClienteDto> response = new ApiResponse<>(true, "Cliente obtenido con éxito", clienteDto);
		return ResponseEntity.ok(response);
	}
	
	
	/*
	 * Este es el método que maneja la solicitud POST para guardar datos en la db
	 */
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ClienteDto clienteDto, BindingResult result){
		//verifica si hay errores de validación en los datos del clienteDto.
		if (result.hasErrors()) {
            return validar(result);
        }
		// verifica si el correo electrónico proporcionado por el cliente ya está registrado por otro cliente.
		if (!clienteDto.getCorreo().isEmpty() && clienteServ.existePorCorreo(clienteDto.getCorreo())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Correo registrado por otro cliente"));
        }
		//se utiliza un objeto modelMapper para mapear el objeto ClienteDto recibido al objeto Cliente, para convertir el dto en entidad
		Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
		// para guardar el cliente en la base de datos. 
		clienteServ.guardarCliente(cliente);
		//para mostrar el mensaje exitoso
		ClienteDto savedCliente = modelMapper.map(cliente, ClienteDto.class);
		ApiResponse<ClienteDto> response = new ApiResponse<>(true, "Departamento guardado con éxito", savedCliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/*
	 * Este es el método que maneja la solicitud DELETE para borrar datos de la db usando un id que se toma mediante @PathVarible
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		// para eliminar el cliente en la base de datos. 
		clienteServ.eliminarCliente(id);
		//para mostrar el mensaje exitoso
		ApiResponse<String> response = new ApiResponse<String>(true, "Cliente eliminado con éxito", null);
		return ResponseEntity.ok(response);
	}	
	

	/*
	 * Este bloque de código itera sobre todos los errores de campo (FieldError) 
	 * en el objeto BindingResult. Por cada error de campo, se agrega una entrada al mapa errores.
	 * Por ejemplo, si el campo "nombre" tiene un error de longitud mínima, 
	 * el mensaje sería "El campo nombre debe tener al menos 3 caracteres".
	 */
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
	
}
