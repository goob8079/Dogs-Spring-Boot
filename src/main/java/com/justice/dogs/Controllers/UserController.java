package com.justice.dogs.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // retrieve user's attributes and return as a map
    @GetMapping("/info")
    public Map<String, Object> userInfo(OAuth2AuthenticationToken auth) {
        return auth.getPrincipal().getAttributes();
    }
}
