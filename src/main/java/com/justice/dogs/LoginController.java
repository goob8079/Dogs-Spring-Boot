package com.justice.dogs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// just a controller for the login page
@Controller
public class LoginController {

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home/registration")
    public String registration() {
        return "registration";
    }
}
