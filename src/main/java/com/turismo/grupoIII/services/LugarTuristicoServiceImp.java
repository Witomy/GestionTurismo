/**
 * @file: LugarTuristicoServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:18:12
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.DetalleRutaTuristicaRepositoryJPA;
import com.turismo.grupoIII.repository.LugarTuristicoRepositoryJPA;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Service
public class LugarTuristicoServiceImp implements LugarTuristicoService {

    @Autowired
    private LugarTuristicoRepositoryJPA lugarTuristicoRepository;
    @Autowired
    private DetalleRutaTuristicaRepositoryJPA detalleRepository;

    @Override
    @Transactional
    public List<LugarTuristico> listarLugaresTuristicos() {
        return lugarTuristicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LugarTuristico> buscarLugarTuristicoPorId(Long idLugarTuristico) throws EntityNotFoundException {
        Optional<LugarTuristico> lugarTuristico = lugarTuristicoRepository.findById(idLugarTuristico);
        if (lugarTuristico.isEmpty()) {
            throw new EntityNotFoundException("Lugar turístico no encontrado");
        }
        return lugarTuristico;
    }

    @Override
    @Transactional
    public LugarTuristico guardarLugarTuristico(LugarTuristico lugarTuristico) throws IlegalOperationException, ConflictException {
        if (lugarTuristico.getIdLugar() != null && lugarTuristicoRepository.existsById(lugarTuristico.getIdLugar())) {
            throw new ConflictException("Ya existe un lugar turístico con el mismo ID");
        }
        return lugarTuristicoRepository.save(lugarTuristico);
    }

    @Override
    @Transactional
    public void eliminarLugarTuristico(Long idLugarTuristico) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<LugarTuristico> lugarTuristico = lugarTuristicoRepository.findById(idLugarTuristico);
        if (lugarTuristico.isEmpty()) {
            throw new EntityNotFoundException("Lugar turístico no encontrado");
        }
        lugarTuristicoRepository.deleteById(idLugarTuristico);
    }
    @Override
    @Transactional
    public LugarTuristico actualizarLugarTuristico(Long idLugarTuristico, LugarTuristico lugarTuristico) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<LugarTuristico> lugarTuristicoEntity = lugarTuristicoRepository.findById(idLugarTuristico);
        if (lugarTuristicoEntity.isEmpty()) {
            throw new EntityNotFoundException("Lugar turístico no encontrado");
        }

        // Lógica de validación y actualización aquí...

        return lugarTuristicoRepository.save(lugarTuristico);
    }
    @Override
	public LugarTuristico asignarLugar(Long idDetalle, Long idLugar) throws EntityNotFoundException, ConflictException, IlegalOperationException {
		// TODO Auto-generated method stub
		//mensaje de error para cuando el cliente no se encuetre en la db
				Optional<DetalleRutaTuristica> detalleRutaEntity = detalleRepository.findById(idDetalle);
				if(detalleRutaEntity.isEmpty()) throw new EntityNotFoundException("Detalle de ruta no encontrado");
				//mensaje de error para cuando no exista una reserva en la db
				Optional<LugarTuristico> lugarEntity = lugarTuristicoRepository.findById(idLugar);
				if(lugarEntity.isEmpty()) throw new EntityNotFoundException("Lugar turístico no encontrado");
				//mensaje de error para cuando el cliente ya se asignÃ³ a otra reserva
				DetalleRutaTuristica detalleAnterior = lugarEntity.get().getDetalleRuta();
			    if (detalleAnterior != null && detalleAnterior.getIdDetalleRuta().equals(idDetalle)) {
			        throw new IlegalOperationException("El detalle proporcionado ya está en uso");
			    }
				
				lugarEntity.get().setDetalleRuta(detalleRutaEntity.get());
				
				return lugarEntity.get();

	}
    
}


