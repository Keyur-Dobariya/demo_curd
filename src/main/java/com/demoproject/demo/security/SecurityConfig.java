package com.demoproject.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/register", "/api/login").permitAll() // Allow access to these endpoints
            .anyRequest().authenticated(); // All other requests require authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return new CustomUserDetailsService(); // Implement this service to load user details
    // }

    // @Bean
    // public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    // }
}