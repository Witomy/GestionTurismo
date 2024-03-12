/**
 * @file: RutaTuristicaServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:18:23
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.RutaTuristica;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.ContratoRepositoryJPA;
import com.turismo.grupoIII.repository.RutaTuristicaRepositoryJPA;

/**
 * Descripción de la clase .
 * Esta clase se utiliza para ...
 */
@Service
public class RutaTuristicaServiceImp implements RutaTuristicaService {

    @Autowired
    private RutaTuristicaRepositoryJPA rutaTuristicaRepository;
    
    @Autowired
    private ContratoRepositoryJPA contratoRepository;

    @Override
    @Transactional
    public List<RutaTuristica> listarRutasTuristicas() {
        return rutaTuristicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RutaTuristica> buscarRutaTuristicaPorId(Long idRutaTuristica) throws EntityNotFoundException {
        Optional<RutaTuristica> rutaTuristica = rutaTuristicaRepository.findById(idRutaTuristica);
        if (rutaTuristica.isEmpty()) {
            throw new EntityNotFoundException("Ruta turística no encontrada");
        }
        return rutaTuristica;
    }

    @Override
    @Transactional
    public RutaTuristica guardarRutaTuristica(RutaTuristica rutaTuristica) throws IlegalOperationException, ConflictException {
        if (rutaTuristica.getIdRuta() != null && rutaTuristicaRepository.existsById(rutaTuristica.getIdRuta())) {
            throw new ConflictException("Ya existe una ruta turística con el mismo ID");
        }
        return rutaTuristicaRepository.save(rutaTuristica);
    }

    @Override
    @Transactional
    public void eliminarRutaTuristica(Long idRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<RutaTuristica> rutaTuristica = rutaTuristicaRepository.findById(idRutaTuristica);
        if (rutaTuristica.isEmpty()) {
            throw new EntityNotFoundException("Ruta turística no encontrada");
        }
        rutaTuristicaRepository.deleteById(idRutaTuristica);
    }
    @Override
    @Transactional
    public RutaTuristica actualizarRutaTuristica(Long idRutaTuristica, RutaTuristica rutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<RutaTuristica> rutaTuristicaEntity = rutaTuristicaRepository.findById(idRutaTuristica);
        if (rutaTuristicaEntity.isEmpty()) {
            throw new EntityNotFoundException("Ruta turística no encontrada");
        }

        // Lógica de validación y actualización aquí...

        return rutaTuristicaRepository.save(rutaTuristica);
    }
    @Override
	@Transactional
	public List<Contrato> listarContratosRuta(Long idRuta) {
		// TODO Auto-generated method stub
		return (List <Contrato>)rutaTuristicaRepository.findById(idRuta).get().getContratos();
	}

}
