package com.justice.dogs;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.justice.dogs.account.UserDTO;
import com.justice.dogs.exceptions.UserAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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

    // verifying if the email account already exists or not
    @PostMapping("/home/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto, HttpServletRequest request, Errors errors) {
        ModelAndView mav = new ModelAndView("registration");

        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistsException uaeEx) {    
            mav.addObject("message", "An account with this email/username already exists!");
            return mav;
        }

        return new ModelAndView("successfulRegister", "user", userDto);
    }
}
