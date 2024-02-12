package com.banking.ank.util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseObject> handleEntityNotFoundException(EntityNotFoundException ex) {
        // Customize the error message as needed
        String errorMessage = "Entity not found: " + ex.getMessage();
        ErrorResponseObject errorResponse = new ErrorResponseObject();
        errorResponse.setMessage(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseObject> handleBadCredintialsException(BadCredentialsException ex){
        String errorMessage = "Incorrect username or password!";
        ErrorResponseObject errorResponse = new ErrorResponseObject();
        errorResponse.setMessage(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED );
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseObject> handleRuntimeException(RuntimeException ex){
        String errorMessage = ex.getMessage();
        ErrorResponseObject errorResponse = new ErrorResponseObject();
        errorResponse.setMessage(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST );
    }
}
