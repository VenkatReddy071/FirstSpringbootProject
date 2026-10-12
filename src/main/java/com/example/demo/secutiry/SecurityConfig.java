
package com.example.demo.secutiry;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAutenticationFilter jwtFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                    // Swagger / OpenAPI documentation
                    .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**"
                    ).permitAll()

                    // Public endpoints
                    .requestMatchers(
                        "/users/login",
                        "/organization/register",
                        "/role/create-role"
                    ).permitAll()

                    
                    .requestMatchers("/organization").authenticated()
                    .requestMatchers("/categories/**").authenticated()
                    .requestMatchers("/expenses/**").authenticated()

                    
                    .anyRequest().authenticated()
                )
            .cors(c->c.configurationSource(configurationSource()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )  .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
   public CorsConfigurationSource configurationSource() {
    	CorsConfiguration config=new CorsConfiguration();
    	config.setAllowedOrigins(List.of("http://localhost:4200","http://localhost:5173"));
    	config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    	config.setAllowCredentials(true);
     UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
    	
    	source.registerCorsConfiguration("/**", config);
    	return source;
    }
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}
