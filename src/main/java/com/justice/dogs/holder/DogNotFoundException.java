package com.justice.dogs.holder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(long id) {
        super("Could not find Dog " + id); 
    }
}

@ControllerAdvice // this advice will get rendered straight into the response body
class DogNotFoundExceptionHelper {

    @ExceptionHandler(DogNotFoundException.class) // the advice will only respond when an DogNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND) // issues HTTP 404 error
    public String dogNotFound(DogNotFoundException ex) {
        return ex.getMessage();
    }
}
