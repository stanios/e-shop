package com.finalproject.cf.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Specific exceptions
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException (
            EntityNotFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoProductsFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleBadRequest (
            NoProductsFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConvertingImgException.class)
    public ResponseEntity<?> handleConvertingImgException (
            ConvertingImgException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException (
            Exception e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationHandling(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                exception.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
