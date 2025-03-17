package com.justice.dogs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// just a controller for the login page
@Controller
public class LoginController {

    @GetMapping("/home/login")
    public String login() {
        return "login";
    }
}
