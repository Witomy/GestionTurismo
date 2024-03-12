/**
 * @file: DetalleRutaTuristicaServiceImp.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 8 mar. 2024 19:17:49
 */
package com.turismo.grupoIII.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turismo.grupoIII.domain.Contrato;
import com.turismo.grupoIII.domain.DetalleRutaTuristica;
import com.turismo.grupoIII.domain.LugarTuristico;
import com.turismo.grupoIII.exceptions.ConflictException;
import com.turismo.grupoIII.exceptions.EntityNotFoundException;
import com.turismo.grupoIII.exceptions.IlegalOperationException;
import com.turismo.grupoIII.repository.DetalleRutaTuristicaRepositoryJPA;

/**
 * Implementación del servicio relacionado con el detalle de la ruta turística.
 */
@Service
public class DetalleRutaTuristicaServiceImp implements DetalleRutaTuristicaService {

    @Autowired
    private DetalleRutaTuristicaRepositoryJPA detalleRutaTuristicaRepository;

    
    /**
     * Recupera todos los detalles de las rutas turísticas.
     * 
     * @return Una lista de todos los detalles de las rutas turísticas.
     */
    @Override
    @Transactional
    public List<DetalleRutaTuristica> listarDetallesRutaTuristica() {
        return detalleRutaTuristicaRepository.findAll();
    }

    /**
     * Busca un detalle de ruta turística por su ID.
     * 
     * @param idDetalleRutaTuristica El ID del detalle de la ruta turística a buscar.
     * @return El detalle de la ruta turística encontrado, si existe.
     * @throws EntityNotFoundException Si no se encuentra el detalle de la ruta turística con el ID especificado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleRutaTuristica> buscarDetalleRutaTuristicaPorId(Long idDetalleRutaTuristica) throws EntityNotFoundException {
        Optional<DetalleRutaTuristica> detalleRutaTuristica = detalleRutaTuristicaRepository.findById(idDetalleRutaTuristica);
        if (detalleRutaTuristica.isEmpty()) {
            throw new EntityNotFoundException("Detalle de ruta turística no encontrado");
        }
        return detalleRutaTuristica;
    }

    /**
     * Guarda un nuevo detalle de ruta turística en la base de datos.
     * 
     * @param detalleRutaTuristica El detalle de la ruta turística a guardar.
     * @return El detalle de la ruta turística guardado.
     * @throws IlegalOperationException Si se intenta guardar un detalle de ruta turística que ya tiene un ID asignado.
     * @throws ConflictException        Si ya existe un detalle de ruta turística con el mismo ID en la base de datos.
     */
    @Override
    @Transactional
    public DetalleRutaTuristica guardarDetalleRutaTuristica(DetalleRutaTuristica detalleRutaTuristica) throws IlegalOperationException, ConflictException {
        if (detalleRutaTuristica.getIdDetalleRuta() != null && detalleRutaTuristicaRepository.existsById(detalleRutaTuristica.getIdDetalleRuta())) {
            throw new ConflictException("Ya existe un detalle de ruta turística con el mismo ID");
        }
        return detalleRutaTuristicaRepository.save(detalleRutaTuristica);
    }

    /**
     * Elimina un detalle de ruta turística de la base de datos.
     * 
     * @param idDetalleRutaTuristica El ID del detalle de la ruta turística a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el detalle de la ruta turística con el ID especificado.
     */
    @Override
    @Transactional
    public void eliminarDetalleRutaTuristica(Long idDetalleRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<DetalleRutaTuristica> detalleRutaTuristica = detalleRutaTuristicaRepository.findById(idDetalleRutaTuristica);
        if (detalleRutaTuristica.isEmpty()) {
            throw new EntityNotFoundException("Detalle de ruta turística no encontrado");
        }
        detalleRutaTuristicaRepository.deleteById(idDetalleRutaTuristica);
    }
    

    /**
     * Actualiza un detalle de ruta turística en la base de datos.
     * 
     * @param idDetalleRuta           El ID del detalle de la ruta turística a actualizar.
     * @param detalleRutaTuristica    El detalle de la ruta turística con los nuevos datos.
     * @return                        El detalle de la ruta turística actualizado.
     * @throws EntityNotFoundException Si no se encuentra el detalle de la ruta turística con el ID especificado.
     */
    @Override
    @Transactional
    public DetalleRutaTuristica actualizarDetalleRutaTuristica(Long idDetalleRuta, DetalleRutaTuristica detalleRutaTuristica) throws EntityNotFoundException, IlegalOperationException, ConflictException {
        Optional<DetalleRutaTuristica> detalleRutaEntity = detalleRutaTuristicaRepository.findById(idDetalleRuta);
        if (detalleRutaEntity.isEmpty()) {
            throw new EntityNotFoundException("Detalle de ruta turística no encontrado");
        }

        // Lógica de validación y actualización aquí...

        return detalleRutaTuristicaRepository.save(detalleRutaTuristica);
    }
    
    /**
     * Lista todos los lugares turísticos asociados a una ruta turística.
     * 
     * @param idRuta El ID de la ruta turística.
     * @return Una lista de los lugares turísticos asociados a la ruta turística.
     */
    @Override
	@Transactional
	public List<LugarTuristico> listarLugaresTuristicos(Long idRuta) {
		// TODO Auto-generated method stub
		return (List <LugarTuristico>)detalleRutaTuristicaRepository.findById(idRuta).get().getLugaresTuristicos();
	}
}