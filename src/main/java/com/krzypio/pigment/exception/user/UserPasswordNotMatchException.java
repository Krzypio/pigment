package com.krzypio.pigment.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserPasswordNotMatchException extends RuntimeException {
    public UserPasswordNotMatchException(String message) {
        super(message);
    }
}
