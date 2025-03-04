package com.justice.dogs.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    // these two are used for regular expressions
    private Pattern pattern;
    private Matcher matcher;
    
    // the email should follow a pattern like: 
    // (beginning(^)) a string[letters, numbers, -+] (1 or more instances of the string(+)), 
    // (0 or more instances(*)) a dot(.) followed by a string[letters, numbers, -] (1 or more instances of the string(+) (this is optional)),
    // an @ symbol,
    // a string[letters, numbers, -] (1 or more instances of the string(+)) , 
    // (0 or more instaces(*)) a string[letters, numbers],
    // (must have a match at the end of the string($)) a string containing the domain[.com, .org, .net] (must have at least two letters{2,})
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
