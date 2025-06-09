package com.yourcompany.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .headers(headers -> headers
                                                .frameOptions(frame -> frame.sameOrigin())
                                                .xssProtection(xss -> xss.disable()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                new AntPathRequestMatcher("/api/auth/**"),
                                                                new AntPathRequestMatcher("/api/public/**"),
                                                                new AntPathRequestMatcher("/h2-console/**"))
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
        }
}