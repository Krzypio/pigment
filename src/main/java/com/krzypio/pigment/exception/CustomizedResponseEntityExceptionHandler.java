package com.krzypio.pigment.exception;

import com.krzypio.pigment.exception.user.UserPasswordNotValidException;
import com.krzypio.pigment.exception.user.UserAlreadyExistException;
import com.krzypio.pigment.exception.user.UserNotFoundException;
import com.krzypio.pigment.exception.user.UserPasswordNotMatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> defaultErrorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors())
            defaultErrorMessages.add(error.getDefaultMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, "Validation failed", defaultErrorMessages.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(UserPasswordNotValidException.class)
    protected ResponseEntity<Object> handleUserPasswordNotValidException(UserPasswordNotValidException ex, WebRequest request) throws Exception {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status, "Validation failed" , ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}