package com.justice.dogs.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String string) {
        super(string);
    }
}
