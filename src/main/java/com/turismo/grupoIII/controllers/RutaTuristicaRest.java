/**
 * @file: RutaTuristicaRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:23:45
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

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.dto.ContratoDTO;
import com.turismo.grupoIII.dto.DetalleRutaTuristicaDTO;
import com.turismo.grupoIII.dto.RutaTuristicaDTO;
import com.turismo.grupoIII.services.DetalleRutaTuristicaService;
import com.turismo.grupoIII.services.RutaTuristicaService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los RutaTuristica.
 */
@RestController
@RequestMapping(value = "/api/rutas", headers = "Api-Version=1")
public class RutaTuristicaRest {

    @Autowired
    private RutaTuristicaService rutaTuristicaService;
    
    @Autowired
    private DetalleRutaTuristicaService detalleRutaTuristicaService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todas las rutas turísticas registradas en el sistema.
     * @return ResponseEntity con ApiResponse que contiene la lista de rutas turísticas.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RutaTuristicaDTO>>> obtenerTodas() {
        List<RutaTuristica> rutas = rutaTuristicaService.listarRutasTuristicas();
        List<RutaTuristicaDTO> rutasDtos = rutas.stream().map(ruta -> modelMapper.map(ruta, RutaTuristicaDTO.class)).collect(Collectors.toList());
        
        // Agregar enlaces HATEOAS a cada ruta turística en la lista
        rutasDtos.forEach(rutaDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(RutaTuristicaRest.class).slash(rutaDto.getIdRuta()).withSelfRel();
            rutaDto.add(selfLink);
        });
        
        ApiResponse<List<RutaTuristicaDTO>> response = new ApiResponse<>(true, "Lista de rutas turísticas obtenida con éxito", rutasDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una ruta turística específica por su identificador único.
     * @param id Identificador único de la ruta turística.
     * @return ResponseEntity con ApiResponse que contiene la ruta turística obtenida.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RutaTuristicaDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<RutaTuristica> ruta = rutaTuristicaService.buscarRutaTuristicaPorId(id);
        RutaTuristicaDTO rutaDto = modelMapper.map(ruta.orElse(null), RutaTuristicaDTO.class);
        
        // Agregar enlace HATEOAS que apunta a la lista de contratos asociados a la ruta turística
        Link contratosLink = WebMvcLinkBuilder.linkTo(RutaTuristicaRest.class).slash(id).slash("contratos").withRel("contratos");
        rutaDto.add(contratosLink);
        
        ApiResponse<RutaTuristicaDTO> response = new ApiResponse<>(true, "Ruta turística obtenida con éxito", rutaDto);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtiene todos los contratos asociados a una ruta turística específica.
     * @param idRuta Identificador único de la ruta turística.
     * @return ResponseEntity con ApiResponse que contiene la lista de contratos de la ruta turística.
     */
    @GetMapping("/{idRuta}/contratos")
    public ResponseEntity<?> obteneContratosRuta(@PathVariable Long idRuta) {
        List<Contrato> contratos = rutaTuristicaService.listarContratosRuta(idRuta);
        List<ContratoDTO> contratosDtos = contratos.stream().map(contrato -> modelMapper.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
        
        // Agregar enlaces HATEOAS a cada contrato en la lista
        contratosDtos.forEach(contratoDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(RutaTuristicaRest.class).slash(idRuta).slash("contratos").slash(contratoDto.getIdContrato()).withSelfRel();
            contratoDto.add(selfLink);
        });
        
        ApiResponse<List<ContratoDTO>> response = new ApiResponse<>(true, "Mostrando contratos de la ruta solicitada", contratosDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Guarda una nueva ruta turística en el sistema junto con su detalle de ruta turística asociado.
     *
     * @param rutaTuristicaDto         La información de la ruta turística a guardar.
     * @param detalleRutaTuristicaDto  La información del detalle de la ruta turística asociado.
     * @param result                   El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PostMapping
    public ResponseEntity<?> guardarRutaTuristica(@Valid @RequestBody RutaTuristicaDTO rutaTuristicaDto, @Valid @RequestBody DetalleRutaTuristicaDTO detalleRutaTuristicaDto, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        // Implementa la lógica específica para RutaTuristica...
        RutaTuristica rutaTuristica = modelMapper.map(rutaTuristicaDto, RutaTuristica.class);
        rutaTuristicaService.guardarRutaTuristica(rutaTuristica);
        RutaTuristicaDTO savedRutaTuristica = modelMapper.map(rutaTuristica, RutaTuristicaDTO.class);
        
        DetalleRutaTuristica detalleRutaTuristica = modelMapper.map(detalleRutaTuristicaDto, DetalleRutaTuristica.class);
        detalleRutaTuristicaService.guardarDetalleRutaTuristica(detalleRutaTuristica);
        DetalleRutaTuristicaDTO savedDetalleRutaTuristica = modelMapper.map(detalleRutaTuristica, DetalleRutaTuristicaDTO.class);
        
        ApiResponse<RutaTuristicaDTO> response = new ApiResponse<>(true, "Ruta turística guardada con éxito", savedRutaTuristica);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina una ruta turística del sistema por su ID.
     *
     * @param id El ID de la ruta turística a eliminar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        rutaTuristicaService.eliminarRutaTuristica(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Ruta turística eliminada con éxito", null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza la información de una ruta turística existente en el sistema.
     *
     * @param id       El ID de la ruta turística a actualizar.
     * @param rutaDTO  La información actualizada de la ruta turística.
     * @param result   El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRutaTuristica(@Valid @PathVariable Long id, @RequestBody RutaTuristicaDTO rutaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }

        RutaTuristica rutaActualizada = rutaTuristicaService.actualizarRutaTuristica(id, modelMapper.map(rutaDTO, RutaTuristica.class));
        RutaTuristicaDTO rutaDtoActualizada = modelMapper.map(rutaActualizada, RutaTuristicaDTO.class);

        ApiResponse<RutaTuristicaDTO> response = new ApiResponse<>(true, "Ruta turística actualizada con éxito", rutaDtoActualizada);

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
    

}
