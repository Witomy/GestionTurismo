/**
 * @file: JwtAuthenticationFilter.java
 * @autor: Jheferson Chalan
 * @creado: 12 mar. 2024 04:20:54
 */
package com.turismo.grupoIII.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 
 */
 @Component
 @RequiredArgsConstructor
 public class JwtAuthenticationFilter extends OncePerRequestFilter {

     private final JwtService jwtService;
     private final UserDetailsService userDetailsService;

     /**
      * Procesa la solicitud HTTP para verificar la autenticación del usuario.
      * @param request HttpServletRequest que representa la solicitud HTTP.
      * @param response HttpServletResponse que representa la respuesta HTTP.
      * @param filterChain FilterChain para invocar el siguiente filtro en la cadena.
      * @throws ServletException Si ocurre un error durante la ejecución del filtro.
      * @throws IOException Si ocurre un error de entrada o salida durante la ejecución del filtro.
      */
     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
             throws ServletException, IOException {
        
         final String token = getTokenFromRequest(request);
         final String username;

         if (token == null) {
             filterChain.doFilter(request, response);
             return;
         }

         username = jwtService.getUsernameFromToken(token);

         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             UserDetails userDetails = userDetailsService.loadUserByUsername(username);

             if (jwtService.isTokenValid(token, userDetails)) {
                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                     userDetails,
                     null,
                     userDetails.getAuthorities());

                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
         }
         
         filterChain.doFilter(request, response);
     }

     /**
      * Extrae el token JWT de la solicitud HTTP.
      * @param request HttpServletRequest que representa la solicitud HTTP.
      * @return Token JWT si está presente en la solicitud, de lo contrario, devuelve null.
      */
     private String getTokenFromRequest(HttpServletRequest request) {
         final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

         if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
             return authHeader.substring(7);
         }
         return null;
     }
     
 }

