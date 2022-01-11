package com.krzypio.pigment.exception.other;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductionBatchNotFoundException extends RuntimeException{
    public ProductionBatchNotFoundException(String message) {
        super(message);
    }
}
