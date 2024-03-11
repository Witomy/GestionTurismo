/**
 * @file: ReservaController.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 17:00:49
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

import com.agencia.reservas.Services.ReservaService;
import com.agencia.reservas.domain.Reserva;
import com.agencia.reservas.dto.ReservaDto;
import com.agencia.reservas.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping("api/reserva")
public class ReservaRest {

	@Autowired
	private ReservaService reservaServ;
	
	@Autowired
    private ModelMapper modelMapper;
	
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos las reservas existentes en la db.
	 */
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		List<Reserva> reservas = reservaServ.listarReservas();
		List<ReservaDto> reservaDtos = reservas.stream().map(reserva -> modelMapper.map(reserva, ReservaDto.class)).collect(Collectors.toList());
		ApiResponse<List<ReservaDto>> response = new ApiResponse<>(true, "Reservas de clientes obtenida con éxito", reservaDtos);
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos la reservas existentes en la db haciendo uso de un ID.
	 */	
	@GetMapping("/id")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		Optional<Reserva> reserva = reservaServ.buscarReservaPorId(id);
		ReservaDto reservaDto = modelMapper.map(reserva, ReservaDto.class);
		//para mostrar el mensaje exitoso
		ApiResponse<ReservaDto> response = new ApiResponse<>(true, "Reserva obtenida con éxito", reservaDto);
		return ResponseEntity.ok(response);
	}
	
	//	Este es el método que maneja la solicitud POST para guardar datos en la db	
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ReservaDto reservaDto, BindingResult result){
		//verifica si hay errores de validación en los datos del clienteDto.
		if (result.hasErrors()) {
            return validar(result);
        }
		//se utiliza un objeto modelMapper para mapear el objeto reservaDto recibido al objeto Reserva, para convertir el dto en entidad
		Reserva reserva = modelMapper.map(reservaDto, Reserva.class);
		reservaServ.guardarReserva(reserva);
		//para mostrar el mensaje exitoso
		ReservaDto savedReserva= modelMapper.map(reserva, ReservaDto.class);
		ApiResponse<ReservaDto> response = new ApiResponse<>(true, "Reserva guardada con éxito", savedReserva);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
    // Este es el método que maneja la solicitud DELETE para borrar datos de la db usando un id que se toma mediante @PathVarible
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		reservaServ.eliminarReserva(id);
		ApiResponse<String> response = new ApiResponse<String>(true, "Reserva eliminado con éxito", null);
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
