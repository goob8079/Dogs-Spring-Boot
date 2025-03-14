package com.justice.dogs;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.justice.dogs.account.email.VerificationToken;
import com.justice.dogs.account.user.IUserService;
import com.justice.dogs.account.user.UserDTO;
import com.justice.dogs.account.user.UserService;
import com.justice.dogs.exceptions.UserAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


// just a controller for the login page
@Controller
public class LoginController {

    @Autowired
    private UserService userService = new UserService();

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IUserService service;

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
        try {
            User registered = userService.registerNewUserAccount(userDto);

            // these two lines trigger an ApplicationEventPublisher to publish the registration completion 
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (UserAlreadyExistsException uaeEx) {    
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            mav.addObject("message", "An account with this email/username already exists!");
            return mav;
        } catch (RuntimeException ex) {
            return new ModelAndView("emailError", "user", userDto);
        }

        return new ModelAndView("successfulRegister", "user", userDto);
    }

    @GetMapping("/home/registration/confirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("mesage", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageVal = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageVal);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return "login";
    }
}
