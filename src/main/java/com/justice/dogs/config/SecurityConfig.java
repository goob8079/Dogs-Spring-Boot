package com.justice.dogs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.justice.dogs.users.CustomUserDetailsService;
import com.justice.dogs.users.UserService;

import org.springframework.security.authentication.AuthenticationProvider;

// A class for adding security configurations such as a login feature
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
                .permitAll()
            )
            .logout(logout -> logout
            .logoutUrl("/logout")
            .permitAll());

		return http.build();
    } 

    // makes sure that application can check the username and password from the database properly
    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(userService);
    //     provider.setPasswordEncoder(passwordEncoder());
    //     return provider;
    // }
    
    // used to encode passwords for user accounts
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
