package com.justice.dogs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.justice.dogs.users.UserObj;
import com.justice.dogs.users.UserRepo;

@RestController
public class SignupController {

    @Autowired
    private UserRepo repo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignupController(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping(value = "/home/signup", consumes = "application/json")
    public UserObj createUser(@RequestBody UserObj user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    
}
