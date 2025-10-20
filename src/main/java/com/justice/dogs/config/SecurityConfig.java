package com.justice.dogs.config;

import com.justice.dogs.dogsHolder.DogsRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// A class for adding security configurations such as a login feature
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DogsRepo dogsRepo;

    SecurityConfig(DogsRepo dogsRepo) {
        this.dogsRepo = dogsRepo;
    }
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // using an updated version of the syntax (after Spring Security v6.xx)
        http
            .requiresChannel(channel -> 
                channel.anyRequest().requiresSecure()
            ) // every request must use HTTPS; automatically redirect HTTP requests to HTTPS
            .authorizeHttpRequests(requests -> requests
                // public endpoints (accessible by anyone)
                .requestMatchers("/", "/home", "/dogs/dogtypes", "/dogs/pibbletypes", "/dogs/newdog",
                        "/login",
                        "/js/hidden-pibble.js", "/js/user-access.js",
                        "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated() // any other endpoint not listed requires authentication
            )
            // enable Oauth2 login support
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home/auth", true)
            )
            .logout(l -> l
                .logoutSuccessUrl("/home")
            );
                    
		return http.build();
    } 
}
