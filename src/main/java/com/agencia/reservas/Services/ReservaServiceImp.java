/**
 * @file: ReservaServiceImp.java
 * @autor: Andy Cotrina
 * @creado: 3 mar. 2024 16:02:43
 */
package com.agencia.reservas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agencia.reservas.domain.Cliente;
import com.agencia.reservas.domain.Reserva;
import com.agencia.reservas.exceptions.ConflictException;
import com.agencia.reservas.exceptions.EntityNotFoundException;
import com.agencia.reservas.exceptions.IllegalOperationException;
import com.agencia.reservas.repository.ClienteRepositoryJpa;
import com.agencia.reservas.repository.ReservaRepositoryJpa;

/**
 * Se implementan los métodos que luego se usaran en lo controllers. 
 * se añaden las exceptions
 */
@Service
public class ReservaServiceImp implements ReservaService {

	@Autowired
    private ReservaRepositoryJpa reservaRepository;
	
	@Autowired
    private ClienteRepositoryJpa clienteRepository;
	
	@Override
	public List<Reserva> listarReservas() {
		// TODO Auto-generated method stub
		return (List<Reserva>) reservaRepository.findAll();
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<Reserva> buscarReservaPorId(Long id) throws EntityNotFoundException{
		// TODO Auto-generated method stub
		Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isEmpty()) {
            throw new EntityNotFoundException("Reserva no encontrada");
        }
        return reserva;
	}

	@Override
	public Reserva guardarReserva(Reserva reserva)throws IllegalOperationException {
		// TODO Auto-generated method stub
		return reservaRepository.save(reserva);
	}

	@Override
	@Transactional
	public void eliminarReserva(Long id) {
		// TODO Auto-generated method stub
		if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserva no encontrada");
        }
		reservaRepository.deleteById(id);
	}

	
	@Override
	@Transactional
	public Reserva asignarReserva(Long idCliente, Long idReserva)
			throws EntityNotFoundException, IllegalOperationException, ConflictException {
		// TODO Auto-generated method stub
		
		//mensaje de error para cuando el cliente no se encuetre en la db
		Optional<Cliente> clienteEntity = clienteRepository.findById(idCliente);
		if(clienteEntity.isEmpty()) throw new EntityNotFoundException("Cliente no encontrado");
		//mensaje de error para cuando no exista una reserva en la db
		Optional<Reserva> reservaEntity = reservaRepository.findById(idReserva);
		if(reservaEntity.isEmpty()) throw new EntityNotFoundException("Reserva no encontrado");
		//mensaje de error para cuando el cliente ya se asignó a otra reserva
		Cliente clienteAnterior = reservaEntity.get().getCliente();
	    if (clienteAnterior != null && clienteAnterior.getId().equals(idCliente)) {
	        throw new IllegalOperationException("El cliente proporcionado ya está asignado a esta reserva");
	    }
		
		reservaEntity.get().setCliente(clienteEntity.get());
		
		return reservaEntity.get();
	}

}
