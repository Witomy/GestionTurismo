/**
 * @file: PagoController.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 17:00:38
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

import com.agencia.reservas.Services.PagoService;
import com.agencia.reservas.domain.Pago;
import com.agencia.reservas.dto.PagoDto;
import com.agencia.reservas.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping("api/pago")
public class PagoRest {
	
	@Autowired
	private PagoService pagoServ;
	
	@Autowired
    private ModelMapper modelMapper;
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos los pagos existentes en la db.
	 */
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		List<Pago> pagos = pagoServ.listarPagos();
		List<PagoDto> pagoDtos = pagos.stream().map(pago -> modelMapper.map(pago, PagoDto.class)).collect(Collectors.toList());
		ApiResponse<List<PagoDto>> response = new ApiResponse<>(true, "Lista de cllientes obtenida con éxito", pagoDtos);
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos los pagos existentes en la haciendo uso de un ID.
	 */	
	@GetMapping("/id")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		Optional<Pago> pago = pagoServ.buscarPagoPorId(id);
		PagoDto pagoDto = modelMapper.map(pago, PagoDto.class);
		//Muestra que se realizó con éxito
		ApiResponse<PagoDto> response = new ApiResponse<>(true, "Pago obtenido con éxito", pagoDto);
		return ResponseEntity.ok(response);
	}
	
	//	 Este es el método que maneja la solicitud POST para guardar datos en la db
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody PagoDto pagoDto, BindingResult result){
		//verifica si hay errores de validación en los datos del pagoDto.
		if (result.hasErrors()) {
            return validar(result);
        }
		//se utiliza un objeto modelMapper para mapear el objeto pagoDto recibido al objeto Pago, para convertir el dto en entidad
		Pago pago = modelMapper.map(pagoDto, Pago.class);
		pagoServ.guardarPago(pago);
		//Muestra que se realzió con éxito
		PagoDto savedPago= modelMapper.map(pago, PagoDto.class);
		ApiResponse<PagoDto> response = new ApiResponse<>(true, "Pago guardado con éxito", savedPago);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
    // Este es el método que maneja la solicitud DELETE para borrar datos de la db usando un id que se toma mediante @PathVarible	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		pagoServ.eliminarPago(id);
		ApiResponse<String> response = new ApiResponse<String>(true, "Pago eliminado con éxito", null);
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
