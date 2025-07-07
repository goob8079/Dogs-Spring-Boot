package com.justice.dogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.justice.dogs.services.JwtAuthFilter;
import com.justice.dogs.services.JwtService;

// A class for adding security configurations such as a login feature
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = encoder;
    }
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF is disabled since it is not required for stateless JWT,
            // see README.md.
            .csrf(csrf -> csrf.disable()) 
            // this allows specific paths/pages to be loaded without requiring a login. 
            // /css/** and /img/** need to be added to allow the css templates and images to load
            .authorizeHttpRequests((requests) -> requests
                // public endpoints (accessible by anyone)
                .requestMatchers("/", "/home", "/home/dogslist", "/home/dogtypes", "/home/pibbletypes",
                                "/home/auth", "/home/auth/newUser", "/home/auth/tokenGeneration", "/home/auth/login",
                                "/css/**", "/img/**").permitAll()
                // endpoints for roles (role checks)
                .requestMatchers("/home/auth/users/**").hasAnyAuthority("ROLE_USER")
                .requestMatchers("/home/auth/admin/**").hasAnyAuthority("ROLE_ADMIN")
                // any other endpoint not listed requires authentication
                .anyRequest().authenticated()
            )
            // the session has to be set to stateless for JWT
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // custom authentication provider,
            // can be used for logic such as verifying credentials against a database
            .authenticationProvider(authenticationProvider())
            // JWT Filter before Spring Security's default filter
            .addFilterBefore(new JwtAuthFilter(userDetailsService, jwtService), UsernamePasswordAuthenticationFilter.class);

		return http.build();
    } 

    // Authenticates users based on their stored crendentials
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // required for programmatic authentication (e.g., in /home/auth/tokenGeneration)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
