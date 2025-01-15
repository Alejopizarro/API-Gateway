package com.example.game_services_api.config;

import com.example.game_services_api.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtService jwtService, JwtAuthFilter jwtAuthFilter) {
        this.jwtService = jwtService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Usamos el nuevo método 'authorizeRequests' para manejar permisos en las rutas
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("v1/game/**", "/v1/game/create", "/v1/game/{id}").authenticated()  // Requiere autenticación
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()  // Acceso sin autenticación a Swagger
                        .anyRequest().permitAll()  // Resto de las rutas sin restricciones
                )
                .csrf(csrf -> csrf.disable())  // Desactivar CSRF ya que estamos usando JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Desactivar las sesiones, ya que estamos usando JWT (sin estado)
                );

        // Añadir filtro JWT antes de UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build(); // Configuración del AuthenticationManager
    }
}
