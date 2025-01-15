package com.example.game_services_api.config;

import com.example.game_services_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Obtenemos el header de autorización
        String authHeader = request.getHeader("Authorization");

        // Verificamos si el header contiene un token Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extraemos el token del header
            String jwtToken = authHeader.substring(7);

            // Verificamos si el token no ha expirado
            if (!jwtService.isExpired(jwtToken)) {
                // Extraemos el userId del token
                String userId = jwtService.extractUserId(jwtToken);

                // Creamos un objeto de autenticación con el userId
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, List.of()
                );

                // Establecemos la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuamos con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
