package com.gustavofarias.manageproductsbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF para APIs REST
                .csrf(AbstractHttpConfigurer::disable)

                // Define quais endpoints são públicos
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",                   // endpoints de autenticação
                                "/v3/api-docs/**",            // OpenAPI
                                "/swagger-ui/**",             // Swagger UI
                                "/swagger-ui.html"            // Swagger HTML
                        ).permitAll()
                        .anyRequest().authenticated()      // qualquer outro endpoint exige autenticação
                )

                // Política de sessão stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Adiciona o filtro JWT antes do filtro de autenticação padrão
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
