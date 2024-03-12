/**
 * @file: EmpleadoService.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:22:17
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.Empleado;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
public interface EmpleadoService {
    List<Empleado> listarEmpleados();
    Optional<Empleado> buscarEmpleadoPorId(Long idEmpleado) throws EntityNotFoundException;
    Empleado guardarEmpleado(Empleado empleado) throws IlegalOperationException, ConflictException;
    void eliminarEmpleado(Long idEmpleado) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    Empleado actualizarEmpleado(Long idEmpleado, Empleado empleado) throws EntityNotFoundException, IlegalOperationException, ConflictException;
    public boolean existePordni(String dni);
    public Empleado asignarJefe(Long idEmpleado, Long idJefe) throws EntityNotFoundException, IlegalOperationException;
    public List <Contrato> listarContratosEmpleado(Long idEmpleado);
}
