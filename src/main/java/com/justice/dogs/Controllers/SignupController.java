package com.justice.dogs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.justice.dogs.users.UserObj;

@Controller
public class SignupController {

    
    @GetMapping("/home/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserObj());
        return "signup";
    }
}
