/**
 * @file: JwtService.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:19:41
 */
package com.turismo.grupoIII.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * 
 */@Service
 public class JwtService {

	// Clave secreta para la firma del token
	    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

	    /**
	     * Genera un token JWT para el usuario proporcionado.
	     * @param user Detalles del usuario para los cuales se generará el token.
	     * @return Token JWT generado.
	     */
	    public String getToken(UserDetails user) {
	        return getToken(new HashMap<>(), user);
	    }

	    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
	        return Jwts
	            .builder()
	            .setClaims(extraClaims)
	            .setSubject(user.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 horas de expiración
	            .signWith(getKey(), SignatureAlgorithm.HS256)
	            .compact();
	    }

	    private Key getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	    /**
	     * Obtiene el nombre de usuario desde un token JWT.
	     * @param token Token JWT del cual se obtendrá el nombre de usuario.
	     * @return Nombre de usuario extraído del token.
	     */
	    public String getUsernameFromToken(String token) {
	        return getClaim(token, Claims::getSubject);
	    }

	    /**
	     * Verifica si un token JWT es válido para el usuario proporcionado.
	     * @param token Token JWT a ser validado.
	     * @param userDetails Detalles del usuario a quien se validará el token.
	     * @return true si el token es válido para el usuario proporcionado, false de lo contrario.
	     */
	    public boolean isTokenValid(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    private Claims getAllClaims(String token) {
	        return Jwts
	            .parserBuilder()
	            .setSigningKey(getKey())
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	    }

	    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    private Date getExpiration(String token) {
	        return getClaim(token, Claims::getExpiration);
	    }

	    private boolean isTokenExpired(String token) {
	        return getExpiration(token).before(new Date());
	    }
	    
	}
