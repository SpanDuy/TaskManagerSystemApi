package com.example.TaskManagerSystemApi.controller;

import com.example.TaskManagerSystemApi.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(new ResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handleTokenException(Exception ex) {
        return new ResponseEntity<>(new ResponseException(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(Exception ex) {
        return new ResponseEntity<>(new ResponseException(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return new ResponseEntity<>(new ResponseException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
