package com.idn.backend.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.idn.backend.Filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSecurityHandler handler) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .securityContext(contexConfi -> contexConfi.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(List.of("http://localhost:8080"));
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        config.setAllowedHeaders(List.of("*"));
                        config.setAllowCredentials(true);
                        config.setMaxAge(3600L);
                        return config;
                    }

                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(handler)
                        .accessDeniedHandler(handler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers(HttpMethod.POST, "/private/api/v1/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/public/api/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/public/api/v1/register").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(hbc -> hbc.authenticationEntryPoint(handler));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
