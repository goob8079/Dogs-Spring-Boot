package com.justice.dogs.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // retrieve user's attributes and return as a map
    @GetMapping("/info")
    public Map<String, Object> userInfo(OAuth2AuthenticationToken auth) {
        return auth.getPrincipal().getAttributes();
    }

    @GetMapping("/users/login/oauth2/code/")
    public String userOauth2(Model model) {
        return "userOauth2";
    }
}
