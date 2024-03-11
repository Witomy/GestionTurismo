/**
 * @file: DetalleReservaController.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 17:00:10
 */
package com.agencia.reservas.controllers;

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

import com.agencia.reservas.Services.DetalleReservaService;
import com.agencia.reservas.domain.DetalleReserva;
import com.agencia.reservas.dto.DetalleReservaDto;
import com.agencia.reservas.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping("api/detalleReserva")
public class DetalleReservaRest {
	
	@Autowired
	private DetalleReservaService detalleReservaServ;
	
	@Autowired
    private ModelMapper modelMapper;
	
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos los detalles de reserva existentes.
	 */
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		List<DetalleReserva> detalleReservas = detalleReservaServ.listarDetallesReserva();
		List<DetalleReservaDto> detalleReservaDtos = detalleReservas.stream().map(detalleReserva -> modelMapper.map(detalleReserva, DetalleReservaDto.class)).collect(Collectors.toList());
		ApiResponse<List<DetalleReservaDto>> response = new ApiResponse<>(true, "Lista de detalles de reserva obtenida con éxito", detalleReservaDtos);
		return ResponseEntity.ok(response);
	}
	
	/*llama a la lista de detallesReserva mediante haciendo uso del id
	 * Se llama a un servicio llamado detalleReservaServ para buscar un detalle de reserva por su ID
	 * luego se almacena en un optional
	 */
	@GetMapping("/id")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		Optional<DetalleReserva> detalleReserva = detalleReservaServ.buscarDetallesReservaPorId(id);
		DetalleReservaDto detalleReservaDto = modelMapper.map(detalleReserva, DetalleReservaDto.class);
		
		ApiResponse<DetalleReservaDto> response = new ApiResponse<>(true, "Detalle de reserva obtenido con éxito", detalleReservaDto);
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Este es el método que maneja la solicitud POST para guardar datos en la db
	 */
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody DetalleReservaDto detalleReservaDto, BindingResult result){
		//verifica si hay errores de validación en los datos del DetalleReservaDto.
		if (result.hasErrors()) {
            return validar(result);
        }
		//se utiliza un objeto modelMapper para mapear el objeto DetalleReservaDto recibido al objeto detalleReserva, para convertir el dto en entidad
		DetalleReserva detalleReserva = modelMapper.map(detalleReservaDto, DetalleReserva.class);
		// para guardar en la base de datos. 
		detalleReservaServ.guardarDetallesReserva(detalleReserva);		
		DetalleReservaDto savedDetalleReservaDto = modelMapper.map(detalleReserva, DetalleReservaDto.class);
		//para mostrar el guardado exitoso
		ApiResponse<DetalleReservaDto> response = new ApiResponse<>(true, "Detalle de reserva guardado con éxito", savedDetalleReservaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/*
	 * Este es el método que maneja la solicitud DELETE para eliminar datos en la db
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		//elimina datos de la db
		detalleReservaServ.eliminarDetallesReserva(id);
		ApiResponse<String> response = new ApiResponse<String>(true, "Detalle de reserva eliminado con éxito", null);
		return ResponseEntity.ok(response);
	}
	
	
	/*
	 * Este bloque de código itera sobre todos los errores de campo (FieldError) 
	 * en el objeto BindingResult. Por cada error de campo, se agrega una entrada al mapa errores.
	 */
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
