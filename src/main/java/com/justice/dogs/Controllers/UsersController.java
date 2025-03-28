package com.justice.dogs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justice.dogs.users.IAuthenticationFacade;

@RestController
public class UsersController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @GetMapping("/home/username")
    public String currentUser(Principal principal) {
        Authentication authentication = authenticationFacade.getAuthentication();
        return principal.getName();
    }
    
}
