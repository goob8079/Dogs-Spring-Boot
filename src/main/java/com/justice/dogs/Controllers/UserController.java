package com.justice.dogs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.justice.dogs.services.JwtService;
import com.justice.dogs.services.UserInfoService;
import com.justice.dogs.user.AuthRequest;
import com.justice.dogs.user.UserInfo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;


// this controller is used to retrieve the user information from a mysql database for OAuth2,
// and also to create a new account to register to the database
@Controller
@RequestMapping("/home/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("")
    public String homepage(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/newUser")
    public String newUserForm(Model model) {
        return "auth-newUser";
    }
    
    @PostMapping("/newUser")
    public String addNewUser(@ModelAttribute UserInfo userInfo, Model model) {
        service.addUser(userInfo);
        model.addAttribute("successMsg", "Account has been successfully created");
        return "auth-newUser";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "auth-login";
    }
    
    // There are no role checks because they are already managed in SecurityConfig

    @PostMapping("/tokenGeneration")
    @ResponseBody
    public String authenticationAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Username not found or invalid request!");
        }
    }
    
}
