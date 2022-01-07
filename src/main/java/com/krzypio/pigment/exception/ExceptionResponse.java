package com.krzypio.pigment.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionResponse{
    private Date timestamp;
    private HttpStatus httpStatus;
    private int httpStatusValue;
    private String message;
    private String details;

    public ExceptionResponse(Date timestamp, HttpStatus httpStatus, String message, String details) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.httpStatusValue = httpStatus.value();
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getHttpStatusValue() {
        return httpStatusValue;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
