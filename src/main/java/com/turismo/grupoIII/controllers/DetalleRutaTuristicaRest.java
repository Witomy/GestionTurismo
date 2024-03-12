/**
 * @file: DetalleRutaTuristicaRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:24:33
 */
package com.turismo.grupoIII.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.dto.ContratoDTO;
import com.turismo.grupoIII.dto.DetalleRutaTuristicaDTO;
import com.turismo.grupoIII.dto.LugarTuristicoDTO;
import com.turismo.grupoIII.services.DetalleRutaTuristicaService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los clientes.
 */
@RestController
@RequestMapping(value="/api/detalleRutas",headers = "Api-Version=1")
public class DetalleRutaTuristicaRest {

	
	 /**
    * Servicio para manejar operaciones relacionadas con los clientes.
    */
    @Autowired
    private DetalleRutaTuristicaService detalleRutaTuristicaService;

    /**
     * Mapeador para convertir entidades a DTOs y viceversa.
     */
    @Autowired
    private ModelMapper modelMapper;

    
    /**
     * Obtiene todos los detalles de las rutas turísticas registradas en el sistema.
     * @return ResponseEntity con ApiResponse que contiene la lista de detalles de ruta turística.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DetalleRutaTuristicaDTO>>> obtenerTodos() {
        List<DetalleRutaTuristica> detalles = detalleRutaTuristicaService.listarDetallesRutaTuristica();
        List<DetalleRutaTuristicaDTO> detallesDtos = detalles.stream().map(detalle -> modelMapper.map(detalle, DetalleRutaTuristicaDTO.class)).collect(Collectors.toList());
        // Agregar enlaces HATEOAS a cada detalle de ruta turística en la lista
        detallesDtos.forEach(detalleDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(DetalleRutaTuristicaRest.class).slash(detalleDto.getIdDetalleRuta()).withSelfRel();
            detalleDto.add(selfLink);
        });
        ApiResponse<List<DetalleRutaTuristicaDTO>> response = new ApiResponse<>(true, "Lista de detalles de ruta turística obtenida con éxito", detallesDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un detalle de ruta turística específico por su identificador único.
     * @param id Identificador único del detalle de ruta turística.
     * @return ResponseEntity con ApiResponse que contiene el detalle de ruta turística obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetalleRutaTuristicaDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<DetalleRutaTuristica> detalle = detalleRutaTuristicaService.buscarDetalleRutaTuristicaPorId(id);
        DetalleRutaTuristicaDTO detalleDto = modelMapper.map(detalle.orElse(null), DetalleRutaTuristicaDTO.class);
        // Agregar enlace HATEOAS que apunta a la lista de lugares turísticos asociados al detalle de ruta turística
        Link lugaresLink = WebMvcLinkBuilder.linkTo(DetalleRutaTuristicaRest.class).slash(id).slash("lugares").withRel("lugares");
        detalleDto.add(lugaresLink);
        ApiResponse<DetalleRutaTuristicaDTO> response = new ApiResponse<>(true, "Detalle de ruta turística obtenido con éxito", detalleDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los lugares turísticos asociados a un detalle de ruta turística específico.
     * @param id Identificador único del detalle de ruta turística.
     * @return ResponseEntity con ApiResponse que contiene la lista de lugares turísticos del detalle de ruta turística.
     */
    @GetMapping("/{id}/lugares")
    public ResponseEntity<?> obtenerLugaresDetalle(@PathVariable Long id) {
        List<LugarTuristico> lugaresTuristicos = detalleRutaTuristicaService.listarLugaresTuristicos(id);
        List<LugarTuristicoDTO> lugaresTuristicosDtos = lugaresTuristicos.stream().map(lugarTuristico -> modelMapper.map(lugarTuristico, LugarTuristicoDTO.class)).collect(Collectors.toList());
        // Agregar enlaces HATEOAS a cada lugar turístico en la lista
        lugaresTuristicosDtos.forEach(lugarDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(DetalleRutaTuristicaRest.class).slash(id).slash("lugares").slash(lugarDto.getIdLugar()).withSelfRel();
            lugarDto.add(selfLink);
        });
        ApiResponse<List<LugarTuristicoDTO>> response = new ApiResponse<>(true, "Mostrando lugares turísticos de la ruta solicitada", lugaresTuristicosDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un detalle de ruta turística del sistema por su ID.
     *
     * @param id El ID del detalle de ruta turística a eliminar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        detalleRutaTuristicaService.eliminarDetalleRutaTuristica(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Detalle de ruta turística eliminado con éxito", null);
        return ResponseEntity.ok(response);
    }

    
	
	/**
	 * Actualiza la información de un detalle de ruta turística existente en el sistema.
	 *
	 * @param id          El ID del detalle de ruta turística a actualizar.
	 * @param detalleDTO  La información actualizada del detalle de ruta turística.
	 * @param result      El resultado de la validación de entrada.
	 * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
	 */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DetalleRutaTuristicaDTO>> actualizarDetalleRutaTuristica(@Valid @PathVariable Long id, @RequestBody DetalleRutaTuristicaDTO detalleDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error de validación", null));
        }

        DetalleRutaTuristica detalleActualizado = detalleRutaTuristicaService.actualizarDetalleRutaTuristica(id, modelMapper.map(detalleDTO, DetalleRutaTuristica.class));
        DetalleRutaTuristicaDTO detalleDtoActualizado = modelMapper.map(detalleActualizado, DetalleRutaTuristicaDTO.class);

        ApiResponse<DetalleRutaTuristicaDTO> response = new ApiResponse<>(true, "Detalle de ruta turística actualizado con éxito", detalleDtoActualizado);

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
            errores.put(err.getField(), "Error en " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    
}