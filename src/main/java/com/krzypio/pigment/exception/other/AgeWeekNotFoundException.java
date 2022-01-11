package com.krzypio.pigment.exception.other;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AgeWeekNotFoundException extends RuntimeException{
    public AgeWeekNotFoundException(String message) {
        super(message);
    }
}
