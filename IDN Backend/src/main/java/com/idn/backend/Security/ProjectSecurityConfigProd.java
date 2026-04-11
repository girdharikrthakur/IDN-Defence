package com.idn.backend.security;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.idn.backend.filter.CsrfCookieFilter;
import com.idn.backend.filter.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class ProjectSecurityConfigProd {

        private final JWTTokenValidatorFilter jwtTokenValidatorFilter;
        private final CsrfCookieFilter csrfCookieFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        SecurityExceptionHandler securityExceptionHandler)
                        throws Exception {

                http.cors(config -> config.configurationSource(new CorsConfigurationSource() {

                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("http://localhost:8082"));
                                config.setAllowedMethods(Collections.singletonList("*"));
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                config.setExposedHeaders(List.of("Authorization"));
                                config.setAllowCredentials(true);
                                config.setMaxAge(3600L);
                                return config;
                        }

                }))
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(securityExceptionHandler)
                                                .accessDeniedHandler(securityExceptionHandler))
                                .csrf(csrf -> csrf.disable())
                                .addFilterAfter(csrfCookieFilter, BasicAuthenticationFilter.class)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
                                // .addFilterAfter(jwtTokenGenerationFilter, BasicAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> auth

                                                // Public endpoints (UI + auth)
                                                .requestMatchers(
                                                                "/login",
                                                                "/signup",
                                                                "/login.html",
                                                                "/dashboard",
                                                                "/dashboard.html",
                                                                "/auth/**",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/home",
                                                                "/error/**",
                                                                "/public/**",
                                                                "/api.me")
                                                .permitAll()

                                                // Admin APIs
                                                .requestMatchers("/api/admin/**")
                                                .hasRole("ADMIN")

                                                // Post APIs
                                                .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/posts/**").authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/posts/**").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated()

                                                // Authenticated APIs
                                                .requestMatchers("/api/**", "/private/**").authenticated()
                                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                                .anyRequest().authenticated());

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }
}
