package com.justice.dogs.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home") // the homepage
    public String homepage(Model model) {
        return "index";
    }

    @GetMapping("/home/auth")
    public String homepageAuth(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // get user details from OAuth2User
        String username = principal.getAttribute("name");
        String email = principal.getAttribute("email");

        model.addAttribute("username", username);
        model.addAttribute("email", email);

        return "index-auth";
    }
}
