/**
 * @file: LugarTuristicoRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:24:02
 */
package com.turismo.grupoIII.controllers;

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

import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.dto.LugarTuristicoDTO;
import com.turismo.grupoIII.services.LugarTuristicoService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los LugarTuristico.
 */
@RestController
@RequestMapping(value = "/api/lugares", headers = "Api-Version=1")
public class LugarTuristicoRest {
	 /**
	    * Servicio para manejar operaciones relacionadas con los lugarTuristico.
	    */
    @Autowired
    private LugarTuristicoService lugarTuristicoService;
    /**
     * Mapeador para convertir entidades a DTOs y viceversa.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los lugares turísticos registrados en el sistema.
     * @return ResponseEntity con ApiResponse que contiene la lista de lugares turísticos.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LugarTuristicoDTO>>> obtenerTodos() {
        List<LugarTuristico> lugares = lugarTuristicoService.listarLugaresTuristicos();
        List<LugarTuristicoDTO> lugaresDtos = lugares.stream().map(lugar -> modelMapper.map(lugar, LugarTuristicoDTO.class)).collect(Collectors.toList());
        
        // Agregar enlaces HATEOAS a cada lugar turístico en la lista
        lugaresDtos.forEach(lugarDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(LugarTuristicoRest.class).slash(lugarDto.getIdLugar()).withSelfRel();
            lugarDto.add(selfLink);
        });
        
        ApiResponse<List<LugarTuristicoDTO>> response = new ApiResponse<>(true, "Lista de lugares turísticos obtenida con éxito", lugaresDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un lugar turístico específico por su identificador único.
     * @param id Identificador único del lugar turístico.
     * @return ResponseEntity con ApiResponse que contiene el lugar turístico obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LugarTuristicoDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<LugarTuristico> lugar = lugarTuristicoService.buscarLugarTuristicoPorId(id);
        LugarTuristicoDTO lugarDto = modelMapper.map(lugar.orElse(null), LugarTuristicoDTO.class);
        
        // Agregar enlace HATEOAS que apunta a la lista de lugares turísticos
        Link lugaresLink = WebMvcLinkBuilder.linkTo(LugarTuristicoRest.class).withRel("lugares");
        lugarDto.add(lugaresLink);
        
        ApiResponse<LugarTuristicoDTO> response = new ApiResponse<>(true, "Lugar turístico obtenido con éxito", lugarDto);
        return ResponseEntity.ok(response);
    }

    
    /**
     * Guarda un nuevo lugar turístico en el sistema.
     *
     * @param lugarTuristicoDto La información del lugar turístico a guardar.
     * @param result             El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PostMapping
    public ResponseEntity<?> guardarLugarTuristico(@Valid @RequestBody LugarTuristicoDTO lugarTuristicoDto, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        // Implementa la lógica específica para LugarTuristico...
        LugarTuristico lugarTuristico = modelMapper.map(lugarTuristicoDto, LugarTuristico.class);
        lugarTuristicoService.guardarLugarTuristico(lugarTuristico);
        LugarTuristicoDTO savedLugarTuristico = modelMapper.map(lugarTuristico, LugarTuristicoDTO.class);
        ApiResponse<LugarTuristicoDTO> response = new ApiResponse<>(true, "Lugar turístico guardado con éxito", savedLugarTuristico);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina un lugar turístico del sistema por su ID.
     *
     * @param id El ID del lugar turístico a eliminar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        lugarTuristicoService.eliminarLugarTuristico(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Lugar turístico eliminado con éxito", null);
        return ResponseEntity.ok(response);
    }

    
    /**
     * Actualiza la información de un lugar turístico existente en el sistema.
     *
     * @param id       El ID del lugar turístico a actualizar.
     * @param lugarDTO La información actualizada del lugar turístico.
     * @param result   El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LugarTuristicoDTO>> actualizarLugarTuristico(@Valid @PathVariable Long id, @RequestBody LugarTuristicoDTO lugarDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error de validación", null));
        }

        LugarTuristico lugarActualizado = lugarTuristicoService.actualizarLugarTuristico(id, modelMapper.map(lugarDTO, LugarTuristico.class));
        LugarTuristicoDTO lugarDtoActualizado = modelMapper.map(lugarActualizado, LugarTuristicoDTO.class);

        ApiResponse<LugarTuristicoDTO> response = new ApiResponse<>(true, "Lugar turístico actualizado con éxito", lugarDtoActualizado);

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
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    
    /**
     * Asigna un lugar turístico a un detalle de ruta turística específico.
     *
     * @param idDet El ID del detalle de ruta turística al que se va a asignar el lugar turístico.
     * @param idRut El ID del lugar turístico que se va a asignar.
     * @return El lugar turístico actualizado.
     */
    @PutMapping(value = "/{idDet}/{idRut}")
	public LugarTuristico replaceCliente(@PathVariable Long idDet, @PathVariable Long idRut) {
		LugarTuristico lugarEntity = lugarTuristicoService.asignarLugar(idDet, idRut);
		return lugarEntity;
    }
}