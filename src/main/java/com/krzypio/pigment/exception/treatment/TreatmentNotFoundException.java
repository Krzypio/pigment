package com.krzypio.pigment.exception.treatment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TreatmentNotFoundException extends RuntimeException{
    public TreatmentNotFoundException(String message) {
        super(message);
    }
}
