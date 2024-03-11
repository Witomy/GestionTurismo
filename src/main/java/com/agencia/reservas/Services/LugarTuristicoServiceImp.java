/**
 * @file: LugarTuristicoServiceImp.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 16:01:48
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agencia.reservas.domain.LugarTurisitico;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;
import com.agencia.reservas.repository.LugarTuristicoRepositoryJpa;

/**
 * Se implementan los métodos que luego se usaran en lo controllers. 
 * se añaden las exceptions
 */
@Service
public class LugarTuristicoServiceImp implements LugarTurisiticoService {

	@Autowired
    private LugarTuristicoRepositoryJpa lugarTuristicoRepository;
	
	@Override
	public List<LugarTurisitico> listarLugaresTuristicos() {
		// TODO Auto-generated method stub
		return (List<LugarTurisitico>) lugarTuristicoRepository.findAll();
	}

	
	@Override
    @Transactional(readOnly = true)
	public Optional<LugarTurisitico> buscarLugarTuristicoPorId(Long id) throws EntityNotFoundException{
		Optional<LugarTurisitico> lugarTuristicoOptional = lugarTuristicoRepository.findById(id);
        if (lugarTuristicoOptional.isEmpty()) {
        	//mensaje para cuando no se pueda encontrar el Lugar buscado
            throw new EntityNotFoundException("Lugar turístico no encontrado con ID: " + id);
        }
        return lugarTuristicoOptional;
	}

	@Override
	public LugarTurisitico guardarLugarTuristico(LugarTurisitico lugarTuristico)throws IllegalOperationException {
		if (lugarTuristico.getId() != null) {
			//mensaje para cuando no se puede guardar datos en la db
            throw new IllegalOperationException("No se puede guardar un lugar turístico que ya tiene un ID asignado");
        }
        return lugarTuristicoRepository.save(lugarTuristico);
	}

	@Override
	public void eliminarLugarTuristico(Long id)throws EntityNotFoundException, IllegalOperationException {
		if (!lugarTuristicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Lugar turístico no encontrado con ID: " + id);
        }
        lugarTuristicoRepository.deleteById(id);
	}

}
