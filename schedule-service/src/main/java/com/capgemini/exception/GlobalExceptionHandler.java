package com.capgemini.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific InvalidAirportIdException
    @ExceptionHandler(InvalidAirportIdException.class)
    public ResponseEntity<String> handleInvalidAirportIdException(InvalidAirportIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle specific ScheduleNotFoundException
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<String> handleScheduleNotFoundException(ScheduleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle validation errors for @Valid annotated request bodies
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collect all validation error messages
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        // Return validation errors with BAD_REQUEST status
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}