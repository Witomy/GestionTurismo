/**
 * @file: ContratoServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 22:31:16
 */
package com.turismo.grupoIII.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.Cliente;
import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.Empleado;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.ClienteRepositoryJPA;
import com.turismo.grupoIII.repository.ContratoRepositoryJPA;
import com.turismo.grupoIII.repository.EmpleadoRepositoryJPA;
import com.turismo.grupoIII.repository.RutaTuristicaRepositoryJPA;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Service
public class ContratoServiceImp implements ContratoService {

    @Autowired
    private ContratoRepositoryJPA contratoRepository;
    
    @Autowired
    private ClienteRepositoryJPA clienteRepository;
    
    @Autowired
    private EmpleadoRepositoryJPA empleadoRepository;
    
    @Autowired
    private RutaTuristicaRepositoryJPA rutaTuristicaRepository;

    @Override
    @Transactional
    public List<Contrato> listarContratos() {
        return contratoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrato> buscarContratoPorId(Long idContrato) throws EntityNotFoundException {
        Optional<Contrato> contrato = contratoRepository.findById(idContrato);
        if (contrato.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }
        return contrato;
    }

    @Override
    @Transactional
    public Contrato guardarContrato(Contrato contrato) throws IlegalOperationException, ConflictException {
        if (contrato.getIdContrato() != null && contratoRepository.existsById(contrato.getIdContrato())) {
            throw new ConflictException("Ya existe un contrato con el mismo ID");
        }
        return contratoRepository.save(contrato);
    }

    @Override
    @Transactional
    public void eliminarContrato(Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<Contrato> contrato = contratoRepository.findById(idContrato);
        if (contrato.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }
        contratoRepository.deleteById(idContrato);
    }
    @Override
    @Transactional
    public Contrato actualizarContrato(Long idContrato, Contrato contrato) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<Contrato> contratoEntity = contratoRepository.findById(idContrato);
        if (contratoEntity.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }

        // Lógica de validación y actualización aquí...

        return contratoRepository.save(contrato);
    }
    @Override
    @Transactional
    public Contrato asignarEmpleado(Long idEmpleado, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        
        // Mensaje de error si el empleado no se encuentra en la base de datos
        Optional<Empleado> empleadoEntity = empleadoRepository.findById(idEmpleado);
        if(empleadoEntity.isEmpty()) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }

        // Mensaje de error si no existe un contrato en la base de datos
        Optional<Contrato> contratoEntity = contratoRepository.findById(idContrato);
        if(contratoEntity.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }

        // Mensaje de error si el empleado ya está asignado a otro contrato
        Empleado empleadoAnterior = contratoEntity.get().getEmpleado();
        if (empleadoAnterior != null && empleadoAnterior.getIdEmpleado().equals(idEmpleado)) {
            throw new IlegalOperationException("El empleado proporcionado ya está asignado a este contrato");
        }

        contratoEntity.get().setEmpleado(empleadoEntity.get());
        
        return contratoEntity.get();
    }
    @Override
    @Transactional
    public Contrato asignarCliente(Long idCliente, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException {

        // Mensaje de error si el cliente no se encuentra en la base de datos
        Optional<Cliente> clienteEntity = clienteRepository.findById(idCliente);
        if (clienteEntity.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }

        // Mensaje de error si no existe un contrato en la base de datos
        Optional<Contrato> contratoEntity = contratoRepository.findById(idContrato);
        if (contratoEntity.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }

        // Mensaje de error si el cliente ya está asignado a otro contrato
        Cliente clienteAnterior = contratoEntity.get().getCliente();
        if (clienteAnterior != null && clienteAnterior.getIdCliente().equals(idCliente)) {
            throw new IlegalOperationException("El cliente proporcionado ya está asignado a este contrato");
        }

        contratoEntity.get().setCliente(clienteEntity.get());

        return contratoEntity.get();
    }
    @Override
	@Transactional
	public List<RutaTuristica> listarRutasContrato(Long idContrato) {
		// TODO Auto-generated method stub
		return (List <RutaTuristica>)contratoRepository.findById(idContrato).get().getRutasTuristicas();
	}
    @Override
    @Transactional
    public Contrato asignarRutaTuristica(Long idRutaTuristica, Long idContrato) throws EntityNotFoundException, IlegalOperationException, ConflictException {

        // Mensaje de error si la ruta turística no se encuentra en la base de datos
        Optional<RutaTuristica> rutaTuristicaEntity = rutaTuristicaRepository.findById(idRutaTuristica);
        if (rutaTuristicaEntity.isEmpty()) {
            throw new EntityNotFoundException("Ruta turística no encontrada");
        }

        // Mensaje de error si no existe un contrato en la base de datos
        Optional<Contrato> contratoEntity = contratoRepository.findById(idContrato);
        if (contratoEntity.isEmpty()) {
            throw new EntityNotFoundException("Contrato no encontrado");
        }

        // Mensaje de error si la ruta turística ya está asignada a otro contrato
        List<RutaTuristica> rutasTuristicasAnteriores = contratoEntity.get().getRutasTuristicas();
        if (rutasTuristicasAnteriores != null && rutasTuristicasAnteriores.contains(rutaTuristicaEntity.get())) {
            throw new IlegalOperationException("La ruta turística proporcionada ya está asignada a este contrato");
        }

        // Agregar la nueva ruta turística a la lista de rutas turísticas del contrato
        List<RutaTuristica> rutasTuristicas = contratoEntity.get().getRutasTuristicas();
        if (rutasTuristicas == null) {
            rutasTuristicas = new ArrayList<>();
            contratoEntity.get().setRutasTuristicas(rutasTuristicas);
        }

        rutasTuristicas.add(rutaTuristicaEntity.get());

        return contratoEntity.get();
    }


}

