package com.justice.dogs.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.justice.dogs.users.UserObj;

import org.springframework.ui.Model;

@Controller
public class LoginController {
    
    private boolean authenticate(String username, String password, String email) {
        return "correctUsername".equals(username) && "correctPassword".equals(password) && "correctEmail".equals(email);
    }

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/home/login")
    public String validLogin(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        boolean isValidUser = authenticate(username, password, email);

        if (isValidUser) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password or email");
            return "login";
        }
    }

    @GetMapping("/home/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserObj());
        return "signup";
    }
}