package com.justice.dogs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
public class LoginController {
    
    private AuthenticationManager authenticationManager;

    private Authentication authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/home/login")
    public String validLogin(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}