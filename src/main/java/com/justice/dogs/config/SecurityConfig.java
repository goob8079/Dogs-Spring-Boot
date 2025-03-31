package com.justice.dogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                                "/home/login", "/home/signup", "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/home/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
            .logoutUrl("/logout")
            .permitAll());

		return http.build();
    } 
    
    // used to encode passwords for user accounts
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
