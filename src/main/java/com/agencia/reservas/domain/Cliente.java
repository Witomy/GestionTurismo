/**
 * @file: Cliente.java
 * @autor: Yeltsin Arrestegui
 * @creado: 2 mar. 2024 09:18:18
 */
package com.agencia.reservas.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *  Clase que representa a un cliente 
 */
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cliente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Validación para no permitir datos en blanco
	@NotBlank(message = "El DNI no puede estar en blanco")
	//Validación determinado número de caracteres
    @Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 dígitos numéricos")
    private String dni;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "El nombre no puede estar en blanco")
	//Validación determinado número de caracteres para el nombre
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "Los apellidos no pueden estar en blanco")
    //Validación determinado número de caracteres para el apellido
    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres")
    private String apellidos;

	//Validación para no permitir datos en blanco   
    @NotBlank(message = "El correo no puede estar en blanco")
    //Validación para no permitir valores diferentes al de un formato de Email
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(unique = true)
    private String correo;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "El teléfono no puede estar en blanco")
	//Validación determinado número de caracteres
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de teléfono debe iniciar con 9 y tener un total de 9 dígitos")
    private String telefono;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "La dirección no puede estar en blanco")
    private String direccion;

	//Validación para no permitir datos en blanco
    @NotNull(message = "La fecha de nacimiento no puede estar en blanco")
	//Validación para no permitir que la fecha de nacimiento sea actual o futura, si o sí tiene que ser pasada
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

	//Validación para no permitir datos en blanco
    @NotBlank(message = "El género no puede estar en blanco")
    //Validación para no aceptar valores distintos a los ya establecidos
    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El género debe ser Masculino, Femenino u Otro")
    private String genero;

    /*
     * @OneToMamy: Relación de uno a muchos entre la entidad cliente y reseva,
     * ya que un cliente puede tener varias reservas
     * mappedBy:  la relación está mapeada por el campo llamado "cliente" en la clase Reserva
     */
    @OneToMany(mappedBy = "cliente")
    //@JsonManagedReference
    private List<Reserva> reservas = new ArrayList<>();
}
