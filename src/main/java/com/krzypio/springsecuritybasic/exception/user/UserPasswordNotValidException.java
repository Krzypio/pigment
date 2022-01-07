package com.krzypio.springsecuritybasic.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserPasswordNotValidException extends RuntimeException {
    public UserPasswordNotValidException(String message) {
        super(message);
    }
}
