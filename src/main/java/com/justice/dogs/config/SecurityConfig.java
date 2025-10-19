package com.justice.dogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// A class for adding security configurations such as a login feature
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // this allows specific paths/pages to be loaded without requiring a login. 
        // /css/** and /img/** need to be added to allow the css templates and images to load
        http
            .authorizeHttpRequests((requests) -> requests
            // public endpoints (accessible by anyone)
            .requestMatchers("/", "/home", "/dogs/dogslist", "/dogs/dogtypes", "/dogs/pibbletypes",
                            "/home/auth", "/home/auth/newUser", "/home/auth/tokenGeneration", "/home/auth/login",
                            "/dogs/newdog",
                            "/js/hidden-pibble.js",
                            "/css/**", "/img/**").permitAll()
            .anyRequest().authenticated() // any other endpoint not listed requires authentication
        );
                    
		return http.build();
    } 
}
