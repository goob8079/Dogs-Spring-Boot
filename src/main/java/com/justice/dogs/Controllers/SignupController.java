package com.justice.dogs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justice.dogs.users.UserObj;
import com.justice.dogs.users.UserRepo;
import com.justice.dogs.users.UserService;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/home/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserObj());
        return "signup";
    }

    @PostMapping(value = "/home/signup", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody UserObj user) {
        try {
            userService.saveUser(user.getUsername(), user.getEmail(), user.getPassword());
            return new ResponseEntity<>("Account created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Signup failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }
}
