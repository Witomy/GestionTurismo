/**
 * @file: LugarTuristicoController.java
 * @autor: Jheferson Chalan
 * @creado: 3 mar. 2024 17:00:27
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

import com.agencia.reservas.Services.LugarTurisiticoService;
import com.agencia.reservas.domain.LugarTurisitico;
import com.agencia.reservas.dto.LugarTuristicoDto;
import com.agencia.reservas.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping("api/lugarTuristico")
public class LugarTuristicoRest {
	
	@Autowired
	private LugarTurisiticoService lugarTurisServ;
	
	@Autowired
    private ModelMapper modelMapper;
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos loslugares tusrístico existentes en la db.
	 */
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		List<LugarTurisitico> lugaresTuristico = lugarTurisServ.listarLugaresTuristicos();
		List<LugarTuristicoDto> lugaresDtos = lugaresTuristico.stream().map(lugarTuristico -> modelMapper.map(lugarTuristico, LugarTuristicoDto.class)).collect(Collectors.toList());
		ApiResponse<List<LugarTuristicoDto>> response = new ApiResponse<>(true, "Lista de lugares turísticos obtenida con éxito", lugaresDtos);
		return ResponseEntity.ok(response);
	}
	
	
	/*
	 * Este es el método que maneja la solicitud GET para obtener todos loslugares tusrístico existentes en la haciendo uso de un ID.
	 */
	@GetMapping("/id")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		Optional<LugarTurisitico> lugarTuristico = lugarTurisServ.buscarLugarTuristicoPorId(id);
		LugarTuristicoDto lugarTuristicoDto = modelMapper.map(lugarTuristico, LugarTuristicoDto.class);
		//mensaje que nos muestra que se realizó correctamente
		ApiResponse<LugarTuristicoDto> response = new ApiResponse<>(true, "Cliente obtenido con éxito", lugarTuristicoDto);
		return ResponseEntity.ok(response);
	}
	
	// Este es el método que maneja la solicitud POST para guardar datos en la db
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody LugarTuristicoDto lugarDto, BindingResult result){
		if (result.hasErrors()) {
	        return validar(result);
	    }
		// se utiliza un objeto modelMapper para mapear el objeto lugarDto recibido al objeto Lugar, para convertir el dto en entidad
		LugarTurisitico lugarTuristico= modelMapper.map(lugarDto, LugarTurisitico.class);
		// para guardar en la base de datos
		lugarTurisServ.guardarLugarTuristico(lugarTuristico);

		LugarTuristicoDto savedLugar = modelMapper.map(lugarTuristico, LugarTuristicoDto.class);
		ApiResponse<LugarTuristicoDto> response = new ApiResponse<>(true, "Lugar turístico guardado con éxito", savedLugar);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
    // Este es el método que maneja la solicitud DELETE para borrar datos de la db usando un id que se toma mediante @PathVarible
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		//para eliminar de la db
		lugarTurisServ.eliminarLugarTuristico(id);
		ApiResponse<String> response = new ApiResponse<String>(true, "Lugar turístico eliminado con éxito", null);
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
