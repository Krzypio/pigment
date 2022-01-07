package com.krzypio.springsecuritybasic.exception;

import com.krzypio.springsecuritybasic.exception.user.UserAlreadyExistException;
import com.krzypio.springsecuritybasic.exception.user.UserNotFoundException;
import com.krzypio.springsecuritybasic.exception.user.UserPasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) throws Exception {
        HttpStatus status = HttpStatus.CONFLICT;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(UserPasswordNotMatchException.class)
    public final ResponseEntity<Object> handleUserPasswordNotMatchException(UserPasswordNotMatchException ex, WebRequest request) throws Exception {
        HttpStatus status = HttpStatus.CONFLICT;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }
}