package com.example.user_service_api.config;

import com.example.user_service_api.commons.entities.UserModel;
import com.example.user_service_api.repository.UserRepository;
import com.example.user_service_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtención del token JWT desde el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Verificación de que el encabezado Authorization esté presente y tenga el prefijo "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7); // Eliminar "Bearer " del encabezado

            // Extraer el ID del usuario desde el token JWT
            Optional<Integer> userIdOptional = Optional.ofNullable(jwtService.extractedUserId(jwtToken));

            // Si se obtiene un userId, buscar el usuario en la base de datos
            userIdOptional.flatMap(userId -> userRepository.findById(Long.valueOf(userId)))
                    .ifPresent(userDetails -> {
                        // Establecer el ID del usuario como atributo en la solicitud
                        request.setAttribute("X-User-Id", userDetails.getUserId());

                        // Si el token no ha expirado, procesar la autenticación
                        if (!jwtService.isExpired(jwtToken)) {
                            processAuthentication(request, userDetails);
                        }
                    });
        }

        // Continuar con el procesamiento de la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private void processAuthentication(HttpServletRequest request, UserModel userDetails) {
        // Crear un objeto de autenticación con el usuario y sus roles
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        // Establecer los detalles de autenticación en la solicitud
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
