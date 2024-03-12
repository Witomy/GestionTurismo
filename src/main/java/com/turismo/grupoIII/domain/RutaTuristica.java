/**
 * @file: RutaTuristica.java
 * @autor: Mondragon Hernandez, WIlmer Junior
 * @creado: 7 mar. 2024 16:17:04
 */
package com.turismo.grupoIII.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Clase que representa una ruta turística en el sistema.
 * Esta clase contiene información sobre una ruta turística, como su nombre, duración, costo,
 * cantidad de lugares a visitar, y los contratos asociados a ella.
 */
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRuta")
public class RutaTuristica {
	
	/**
     * Identificador único de la ruta turística.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRuta;
	
	/**
     * Nombre de la ruta turística.
     */
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "El nombre debe contener solo letras y espacios")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede estar nulo")
    private String nombre;

	/**
     * Duración de la ruta turística en horas.
     */
    @Min(value = 1, message = "La duración debe ser al menos 1")
    @Digits(integer = 5, fraction = 0, message = "La duración debe ser un número entero válido")
    private int duracion;

    /**
     * Costo por persona para realizar la ruta turística.
     */
    @NotNull(message = "El costo total no puede estar en blanco")
    @Positive(message = "El costo total debe ser un número positivo")
    @Digits(integer = 5, fraction = 0, message = "El costo debe ser un entero ")
    private int costoPersona;
    
    /**
     * Cantidad de lugares turísticos que se visitan en la ruta.
     */
    @Min(value = 1, message = "La duración debe ser al menos 1")
    @Digits(integer = 5, fraction = 0, message = "La duración debe ser un número entero válido")
    private int cantidadLugares;
    
    /**
     * Lista de contratos asociados a esta ruta turística.
     */
    @ManyToMany(mappedBy = "rutasTuristicas")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Contrato> contratos = new ArrayList<>();
    
    /**
     * Detalle específico de la ruta turística, que incluye los lugares turísticos a visitar.
     */
    @OneToOne(mappedBy = "rutaTuristica", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetalleRutaTuristica detalleRutaTuristica;
}
