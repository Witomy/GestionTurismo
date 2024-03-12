/**
 * @file: EmpleadoServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:22:33
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.Empleado;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.EmpleadoRepositoryJPA;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Service
public class EmpleadoServiceImp implements EmpleadoService {

    @Autowired
    private EmpleadoRepositoryJPA empleadoRepository;

    @Override
    @Transactional
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> buscarEmpleadoPorId(Long idEmpleado) throws EntityNotFoundException {
        Optional<Empleado> empleado = empleadoRepository.findById(idEmpleado);
        if (empleado.isEmpty()) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }
        return empleado;
    }

    @Override
    @Transactional
    public Empleado guardarEmpleado(Empleado empleado) throws IlegalOperationException, ConflictException {
        if (empleado.getIdEmpleado() != null && empleadoRepository.existsById(empleado.getIdEmpleado())) {
            throw new ConflictException("Ya existe un empleado con el mismo ID");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public void eliminarEmpleado(Long idEmpleado) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<Empleado> empleado = empleadoRepository.findById(idEmpleado);
        if (empleado.isEmpty()) {
            throw new EntityNotFoundException("Empleado no encontrado");
        }
        empleadoRepository.deleteById(idEmpleado);
    }
        @Override
        @Transactional
        public Empleado actualizarEmpleado(Long idEmpleado, Empleado empleado) throws EntityNotFoundException, IlegalOperationException, ConflictException {
            Optional<Empleado> empleadoEntity = empleadoRepository.findById(idEmpleado);
            if (empleadoEntity.isEmpty()) {
                throw new EntityNotFoundException("Empleado no encontrado");
            }

            // Lógica de validación y actualización aquí...

            return empleadoRepository.save(empleado);
    }
        @Override
    	@Transactional
        public boolean existePordni(String dni) {
            return empleadoRepository.existsBydni(dni);
        }
        
        @Override
        @Transactional
        public Empleado asignarJefe(Long idEmpleado, Long idJefe) throws EntityNotFoundException, IlegalOperationException {
            Empleado empleado = empleadoRepository.findById(idEmpleado)
                    .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + idEmpleado));

            Empleado jefe = empleadoRepository.findById(idJefe)
                    .orElseThrow(() -> new EntityNotFoundException("Jefe no encontrado con ID: " + idJefe));

            // Verificar si la asignación causará una relación circular
            if (tieneRelacionCircular(empleado, jefe)) {
                throw new IlegalOperationException("La asignación causaría una relación circular");
            }

            empleado.setJefe(jefe);
            empleadoRepository.save(empleado);

            return empleado;
        }

        private boolean tieneRelacionCircular(Empleado empleado, Empleado potencialJefe) {
            while (potencialJefe != null) {
                if (potencialJefe.getIdEmpleado().equals(empleado.getIdEmpleado())) {
                    return true;
                }
                potencialJefe = potencialJefe.getJefe();
            }
            return false;
        }
        @Override
    	@Transactional
    	public List<Contrato> listarContratosEmpleado(Long idEmpleado) {
    		// TODO Auto-generated method stub
    		return (List <Contrato>)empleadoRepository.findById(idEmpleado).get().getContratos();
    	}
}
