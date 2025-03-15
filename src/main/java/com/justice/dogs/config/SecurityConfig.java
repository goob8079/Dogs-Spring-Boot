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
		http
            // this allows specific paths/pages to be loaded without requiring a login. 
            // /css/** and /img/** need to be added to allow the css templates and images to load
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/home/dogslist", "/home/dogtypes", "/home/pibbletypes",
                                "/home/registration", "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

		return http.build();
    }
}
