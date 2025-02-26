package com.justice.dogs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.justice.dogs.login.UserDTO;

// just a controller for the login page
@Controller
public class LoginController {

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home/registration")
    public String showRegistrationForm(Model model, WebRequest request) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    } 
}
