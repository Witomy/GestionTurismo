/**
 * @file: ContratoRest.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:23:31
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.dto.ContratoDTO;
import com.turismo.grupoIII.dto.RutaTuristicaDTO;
import com.turismo.grupoIII.services.ClienteService;
import com.turismo.grupoIII.services.ContratoService;
import com.turismo.grupoIII.services.EmpleadoService;
import com.turismo.grupoIII.util.ApiResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para manejar operaciones relacionadas con los contratos.
 */
@RestController
@RequestMapping(value = "/api/contratos", headers = "Api-Version=1")
public class ContratoRest {

	 /**
     * Servicio para manejar operaciones relacionadas con los contratos.
     */
    @Autowired
    private ContratoService contratoService;
    /**
     * Servicio para manejar operaciones relacionadas con los clientes.
     */
    @Autowired
    private ClienteService clienteService;
    /**
     * Mapeador para convertir entidades a DTOs y viceversa.
     */
    @Autowired
    private EmpleadoService empeladoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<Contrato> contratos = contratoService.listarContratos();
        List<ContratoDTO> contratosDtos = contratos.stream().map(contrato -> modelMapper.map(contrato, ContratoDTO.class)).collect(Collectors.toList());
        contratosDtos.forEach(contratoDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(ContratoRest.class).slash(contratoDto.getIdContrato()).withSelfRel();
            contratoDto.add(selfLink);
        });
        ApiResponse<List<ContratoDTO>> response = new ApiResponse<>(true, "Lista de contratos obtenida con éxito", contratosDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un cliente específico por su identificador único.
     * @param id Identificador único del cliente.
     * @return ResponseEntity con ApiResponse que contiene el cliente obtenido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Contrato> contrato = contratoService.buscarContratoPorId(id);
        ContratoDTO contratoDto = modelMapper.map(contrato, ContratoDTO.class);
        Link rutasLink = WebMvcLinkBuilder.linkTo(ContratoRest.class).slash(id).slash("rutas").withRel("rutas");
        contratoDto.add(rutasLink);
        ApiResponse<ContratoDTO> response = new ApiResponse<>(true, "Contrato obtenido con éxito", contratoDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un lista de rutas asociadas a un contrato.
     * @param id Identificador único del contrato.
     * @return ResponseEntity con ApiResponse que contiene el contrato obtenido.
     */
    @GetMapping("/{idContrato}/rutas")
    public ResponseEntity<?> obtenerRutasContrato(@PathVariable Long idContrato) {
        List<RutaTuristica> rutas = contratoService.listarRutasContrato(idContrato);
        List<RutaTuristicaDTO> rutasDtos = rutas.stream().map(ruta -> modelMapper.map(ruta, RutaTuristicaDTO.class)).collect(Collectors.toList());
        rutasDtos.forEach(rutaDto -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(ContratoRest.class).slash(idContrato).slash("rutas").slash(rutaDto.getIdRuta()).withSelfRel();
            rutaDto.add(selfLink);
        });
        ApiResponse<List<RutaTuristicaDTO>> response = new ApiResponse<>(true, "Mostrando rutas del contrato solicitado", rutasDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Guarda un nuevo contrato en el sistema.
     *
     * @param contratoDto La información del contrato a guardar.
     * @param result      El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ContratoDTO contratoDto, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        
        // Validaciones específicas si las hay
        Contrato contrato = modelMapper.map(contratoDto, Contrato.class);
        contratoService.guardarContrato(contrato);
        ContratoDTO savedContrato = modelMapper.map(contrato, ContratoDTO.class);
        ApiResponse<ContratoDTO> response = new ApiResponse<>(true, "Contrato guardado con éxito", savedContrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina un contrato del sistema por su ID.
     *
     * @param id El ID del contrato a eliminar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        contratoService.eliminarContrato(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Contrato eliminado con éxito", null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza la información de un contrato existente en el sistema.
     *
     * @param id          El ID del contrato a actualizar.
     * @param contratoDTO La información actualizada del contrato.
     * @param result      El resultado de la validación de entrada.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales si hay errores.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContratoDTO>> actualizarContrato(@Valid @PathVariable Long id, @RequestBody Contrato contratoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Error de validación", null));
        }

        Contrato contratoActualizado = contratoService.actualizarContrato(id, contratoDTO);
        ContratoDTO contratoDtoActualizado = modelMapper.map(contratoActualizado, ContratoDTO.class);

        ApiResponse<ContratoDTO> response = new ApiResponse<>(true, "Contrato actualizado con éxito", contratoDtoActualizado);

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
    
    /**
     * Asigna un empleado a un contrato específico.
     *
     * @param idEmpleado   El ID del empleado a asignar.
     * @param idContrato   El ID del contrato al que se va a asignar el empleado.
     * @return El contrato actualizado con el empleado asignado.
     */
    @PutMapping(value = "/{idContrato}/asignarEmpleado/{idEmpleado}")
    public Contrato asignarEmpleado(@PathVariable Long idEmpleado, @PathVariable Long idContrato) {
        Contrato conEntity = contratoService.asignarEmpleado(idEmpleado, idContrato);
        return conEntity;
    }
    
    /**
     * Asigna un cliente a un contrato específico.
     *
     * @param idCliente    El ID del cliente a asignar.
     * @param idContrato   El ID del contrato al que se va a asignar el cliente.
     * @return El contrato actualizado con el cliente asignado.
     */
    @PutMapping(value = "/{idContrato}/asignarCliente/{idCliente}")
    public Contrato asignarCliente(@PathVariable Long idCliente, @PathVariable Long idContrato) {
        Contrato conEntity = contratoService.asignarCliente(idCliente, idContrato);
        return conEntity;
    }

    /**
     * Asigna una ruta turística a un contrato específico.
     *
     * @param idContrato         El ID del contrato al que se va a asignar la ruta turística.
     * @param idRutaTuristica    El ID de la ruta turística que se va a asignar.
     * @return ResponseEntity con el resultado de la operación y detalles adicionales.
     */
	 @PutMapping("/{idContrato}/asignarRuta/{idRutaTuristica}")
	 public ResponseEntity<?> asignarRutaTuristica(@PathVariable Long idContrato, @PathVariable Long idRutaTuristica) {
	         Contrato contrato = contratoService.asignarRutaTuristica(idRutaTuristica, idContrato);
	         ContratoDTO contratoDTO = modelMapper.map(contrato, ContratoDTO.class);
	         ApiResponse<ContratoDTO> response = new ApiResponse<>(true, "Ruta turística asignada al contrato correctamente", contratoDTO);
	         return ResponseEntity.status(HttpStatus.OK).body(response);

	 }

    
}