/**
 * @file: EmpleadoRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:22:52
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

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.Empleado;
import com.turismo.grupoIII.dto.ContratoDTO;
import com.turismo.grupoIII.dto.EmpleadoDTO;
import com.turismo.grupoIII.services.EmpleadoService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los empleados.
 */
@RestController
@RequestMapping(value = "/api/empleados", headers = "Api-Version=1")
public class EmpleadoRest{

	 /**
	    * Servicio para manejar operaciones relacionadas con los clientes.
	    */
    @Autowired
    private EmpleadoService empleadoService;
    /**
     * Mapeador para convertir entidades a DTOs y viceversa.
     */
    @Autowired
    private ModelMapper modelMapper;

    
    /**
     * Obtiene todos los empleados registrados en el sistema.
     * @return ResponseEntity con ApiResponse que contiene la lista de empleados.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpleadoDTO>>> obtenerTodos() {
        List<Empleado> empleados = empleadoService.listarEmpleados();
        List<EmpleadoDTO> empleadosDtos = empleados.stream().map(empleado -> modelMapper.map(empleado, EmpleadoDTO.class)).collect(Collectors.toList());
        
        // Agregar enlaces HATEOAS a cada empleado en la lista
        empleadosDtos.forEach(empleadoDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(EmpleadoRest.class).slash(empleadoDto.getIdEmpleado()).withSelfRel();
            empleadoDto.add(selfLink);
        });
        
        ApiResponse<List<EmpleadoDTO>> response = new ApiResponse<>(true, "Lista de empleados obtenida con éxito", empleadosDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un empleado específico por su identificador único.
     * @param id Identificador único del empleado.
     * @return ResponseEntity con ApiResponse que contiene el empleado obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.buscarEmpleadoPorId(id);
        EmpleadoDTO empleadoDto = modelMapper.map(empleado.orElse(null), EmpleadoDTO.class);
        
        // Agregar enlace HATEOAS que apunta a la lista de contratos asociados al empleado
        Link contratosLink = WebMvcLinkBuilder.linkTo(EmpleadoRest.class).slash(id).slash("contratos").withRel("contratos");
        empleadoDto.add(contratosLink);
        
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado obtenido con éxito", empleadoDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los contratos asociados a un empleado específico.
     * @param id Identificador único del empleado.
     * @return ResponseEntity con ApiResponse que contiene la lista de contratos del empleado.
     */
    @GetMapping("/{id}/contratos")
    public ResponseEntity<?> obtenerContratosEmpleado(@PathVariable Long id) {
        List<Contrato> contratos = empleadoService.listarContratosEmpleado(id);
        List<ContratoDTO> contratosDtos = contratos.stream().map(contrato -> modelMapper.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
        
        // Agregar enlaces HATEOAS a cada contrato en la lista
        contratosDtos.forEach(contratoDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(EmpleadoRest.class).slash(id).slash("contratos").slash(contratoDto.getIdContrato()).withSelfRel();
            contratoDto.add(selfLink);
        });
        
        ApiResponse<List<ContratoDTO>> response = new ApiResponse<>(true, "Mostrando contratos del empleado solicitado", contratosDtos);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Guarda un nuevo empleado en el sistema.
     *
     * @param empleadoDto La información del empleado a guardar.
     * @param result      El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PostMapping
    public ResponseEntity<?> guardarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDto, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (!empleadoDto.getDni().isEmpty() && empleadoService.existePordni(empleadoDto.getDni())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "DNI registrado por otro empleado"));
        }
        // Implementa la lógica específica para Empleado...
        Empleado empleado = modelMapper.map(empleadoDto, Empleado.class);
        empleadoService.guardarEmpleado(empleado);
        EmpleadoDTO savedEmpleado = modelMapper.map(empleado, EmpleadoDTO.class);
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado guardado con éxito", savedEmpleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina un empleado del sistema por su ID.
     *
     * @param id El ID del empleado a eliminar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Empleado eliminado con éxito", null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza la información de un empleado existente en el sistema.
     *
     * @param id          El ID del empleado a actualizar.
     * @param empleadoDTO La información actualizada del empleado.
     * @param result      El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> actualizarEmpleado(@Valid @PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error de validación", null));
        }

        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(id, modelMapper.map(empleadoDTO, Empleado.class));
        EmpleadoDTO empleadoDtoActualizado = modelMapper.map(empleadoActualizado, EmpleadoDTO.class);

        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado actualizado con éxito", empleadoDtoActualizado);

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
     * Asigna un jefe a un empleado específico.
     *
     * @param idEmpleado El ID del empleado al que se va a asignar el jefe.
     * @param idJefe     El ID del jefe que se va a asignar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @PutMapping("/{idEmpleado}/asignarJefe/{idJefe}")
    public ResponseEntity<?> asignarJefe(@PathVariable Long idEmpleado, @PathVariable Long idJefe) {
            Empleado empleado = empleadoService.asignarJefe(idEmpleado, idJefe);
            return ResponseEntity.ok(empleado);
    }
}