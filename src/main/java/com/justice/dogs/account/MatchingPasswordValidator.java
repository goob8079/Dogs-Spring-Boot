package com.justice.dogs.account;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, Object> {

    @Override
    public void initialize(MatchingPassword constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserDTO user = (UserDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
