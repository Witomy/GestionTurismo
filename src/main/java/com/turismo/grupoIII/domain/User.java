/**
 * @file: User.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:10:51
 */
package com.turismo.grupoIII.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa a un usuario en el sistema.
 * Esta clase implementa la interfaz UserDetails de Spring Security para la gestión de la autenticación y autorización.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
	/**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue
    Integer id;

    /**
     * Nombre de usuario del usuario.
     */
    @Basic
    @Column(nullable = false)
    String username;

    /**
     * Apellido del usuario.
     */
    @Column(nullable = false)
    String lastname;

    /**
     * Nombre del usuario.
     */
    String firstname;

    /**
     * País del usuario.
     */
    String country;

    /**
     * Contraseña del usuario.
     */
    String password;

    /**
     * Rol del usuario.
     */
    @Enumerated(EnumType.STRING) 
    Role role;

    /**
     * Obtiene las autoridades del usuario.
     * @return Lista de autoridades del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * @return true si la cuenta no ha expirado, false en caso contrario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * @return true si la cuenta no está bloqueada, false en caso contrario.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     * @return true si las credenciales no han expirado, false en caso contrario.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     * @return true si el usuario está habilitado, false en caso contrario.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}