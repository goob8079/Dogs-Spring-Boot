package com.justice.dogs.services;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// contains custom security logic before requests reach any controller to be handled.
// verifies the user's token and allows access to protected endpoints
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService= jwtService;
    }

    // the protected keyword means this method is accessible within its own package amnd by subclasses
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // extracts "Authorization" header;
        // if it starts with "Bearer ", the actual JWT token is extracted.
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // if a token is present and valid, the username is extracted from it.
            username = jwtService.extractUsername(token);
        }

        // checks if the user is already authenticated.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // the user's details are validated against JWT,
            // if valid, Spring Security authentication is set up using UsernamePasswordAuthenticationToken
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // passes the request/reponse to the next filter in the chain.
        // if this is skipped, the request won't reach any controllers.
        filterChain.doFilter(request, response);
    }
}
