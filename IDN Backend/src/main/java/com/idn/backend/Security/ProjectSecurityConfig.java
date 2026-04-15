package com.idn.backend.security;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.idn.backend.filter.CsrfCookieFilter;
import com.idn.backend.filter.JWTTokenValidatorFilter;
import com.idn.backend.service.impl.OAuth2SuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@Profile("default")
@RequiredArgsConstructor
public class ProjectSecurityConfig {

        private final JWTTokenValidatorFilter jwtTokenValidatorFilter;
        private final CsrfCookieFilter csrfCookieFilter;

        private final OAuth2SuccessHandler oAuth2SuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        SecurityExceptionHandler securityExceptionHandler)
                        throws Exception {

                http.cors(config -> config.configurationSource(new CorsConfigurationSource() {

                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:5173"));
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
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .addFilterBefore(jwtTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
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
                                                                "/home.html",
                                                                "/error/**",
                                                                "/public/**",
                                                                "/oauth2/**")
                                                .permitAll()

                                                // Admin APIs
                                                .requestMatchers("/api/admin/**")
                                                .hasRole("ADMIN")
                                                .requestMatchers("/api/me").authenticated()

                                                // Post APIs
                                                .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/posts/**").authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/posts/**").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated()

                                                // Authenticated APIs
                                                .requestMatchers("/api/**", "/private/**").authenticated()
                                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(Customizer.withDefaults())
                                .oauth2Login(oauth -> oauth
                                                .loginPage("/login")
                                                .successHandler(oAuth2SuccessHandler));

                return http.build();
        }

        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
                ClientRegistration github = githubClientRegistration();
                // ClientRegistration google = googleClientRegistration();
                // ClientRegistration facebook = facebookClientRegistration();

                return new InMemoryClientRegistrationRepository(github);

        }

        private ClientRegistration githubClientRegistration() {
                return CommonOAuth2Provider.GITHUB
                                .getBuilder("github")
                                .clientId("Ov23liiFl9mhHvJhVpH2")
                                .clientSecret("3a4c19e9c5e4bd4a9ae67b7d29056552581fa9b0")
                                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                                .scope("read:user", "user:email")
                                .build();
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
