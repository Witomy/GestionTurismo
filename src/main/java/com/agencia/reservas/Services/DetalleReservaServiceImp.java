/**
 * @file: DetalleReservaServiceImp.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 16:01:09
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agencia.reservas.domain.DetalleReserva;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;
import com.agencia.reservas.repository.DetalleReservaRepositoryJpa;

/**
 * Se implementan los métodos que luego se usaran en lo controllers. 
 * se añaden las exceptions
 */
@Service
public class DetalleReservaServiceImp implements DetalleReservaService {

	@Autowired
    private DetalleReservaRepositoryJpa detallesReservaRepository;
	
	
	@Override
	public List<DetalleReserva> listarDetallesReserva() {
		// TODO Auto-generated method stub
		return (List<DetalleReserva>) detallesReservaRepository.findAll();
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<DetalleReserva> buscarDetallesReservaPorId(Long id) throws EntityNotFoundException{
		// TODO Auto-generated method stub
		Optional<DetalleReserva> detallesReserva = detallesReservaRepository.findById(id);
        if (detallesReserva.isEmpty()) {
        	//mensaje para cuando no se pueda encontrar datos en la db
            throw new EntityNotFoundException("Detalle de reserva no encontrado");
        }
        return detallesReserva;
	}

	@Override
	public DetalleReserva guardarDetallesReserva(DetalleReserva detallesReserva) {
		// TODO Auto-generated method stub
		return detallesReservaRepository.save(detallesReserva);
	}

	@Override
	public void eliminarDetallesReserva(Long id) throws EntityNotFoundException, IllegalOperationException{
		// TODO Auto-generated method stub
		if (!detallesReservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Detalle de reserva no encontrado");
        }
        detallesReservaRepository.deleteById(id);
	}

}
