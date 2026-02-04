package com.idn.backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSecurityHandler handler) throws Exception {
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(handler)
                        .accessDeniedHandler(handler))
                .sessionManagement(smc -> smc.invalidSessionUrl("/invalidsession").maximumSessions(1))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
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
