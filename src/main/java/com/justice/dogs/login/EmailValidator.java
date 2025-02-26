package com.justice.dogs.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    // these two are used for regular expressions
    private Pattern pattern;
    private Matcher matcher;
    
    // this variable uses regular expressions to define how an email should be.
    // ^ indicates the beginning, so the first part must include any of these strings: A-Z, a-z, 0-9, -, and + .
    // the + after the first brackets means that there can be 0 or more instances of a string containing what is in the parentheses
    // which must be followed by an @.
    // after the @, there must be a string containing what is in the brackets,
    // which can then be followed by 0 or more instances of what is inside the parentheses.
    // the final parentheses indicates that there must be a . followed by a domain (such as .com, .org, etc.)
    private static final String EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+ (.[_A-Za-z0-9-]+)* @" + 
    "[A-Za-z0-9-]+ (.[A-Za-z0-9]+)* (.[A-Za-z]{2,})$");

    @Override
    public void initialize(ValidEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return validateEmail(email);
    }

    // this part essentially checks all the constraints that were put on the EMAIL_PATTERN variable
    private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
