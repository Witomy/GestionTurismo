/**
 * @file: PagoServiceImp.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 16:02:21
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agencia.reservas.domain.Pago;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;
import com.agencia.reservas.repository.PagoRepositoryJpa;

/**
 * Se implementan los métodos que luego se usaran en lo controllers. 
 * se añaden las exceptions
 */
@Service
public class PagoServiceImp implements PagoService {

	@Autowired
    private PagoRepositoryJpa pagoRepository;
	
	@Override
	public List<Pago> listarPagos() {
		// TODO Auto-generated method stub
		return (List<Pago>) pagoRepository.findAll();
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<Pago> buscarPagoPorId(Long id) throws EntityNotFoundException{
		Optional<Pago> pagoOptional = pagoRepository.findById(id);
        if (pagoOptional.isEmpty()) {
            throw new EntityNotFoundException("Pago no encontrado con ID: " + id);
        }
        return pagoOptional;
	}

	@Override
	public Pago guardarPago(Pago pago) throws IllegalOperationException{
		// TODO Auto-generated method stub
		return pagoRepository.save(pago);
	}

	@Override
	public void eliminarPago(Long id)throws EntityNotFoundException, IllegalOperationException {
		if (!pagoRepository.existsById(id)) {
			//mensaje para cuando no se encuentre el id del pago que se quiere eliminar
            throw new EntityNotFoundException("No se pudo encontrar el pago con ID: " + id);
        }
        try {
            pagoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
			//mensaje para cuando no este permitido eliminar un pago
            throw new IllegalOperationException("No se puede eliminar el pago con ID: " + id + " debido a restricciones de integridad de datos");
        }
	}

}
