
package com.capgemini.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler for ConstraintViolationException (for validation errors)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        // Create a list to hold all error messages
        List<String> errorMessages = new ArrayList<>();

        // Extract all constraint violations
        Set<jakarta.validation.ConstraintViolation<?>> violations = ex.getConstraintViolations();

        // Add each violation message to the list
        for (jakarta.validation.ConstraintViolation<?> violation : violations) {
            errorMessages.add(violation.getMessage());  // Use the message defined in the annotation
        }

        // Return a response entity with the error messages and HTTP status BAD_REQUEST
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Handler for AirportNotFoundException (custom exception)
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<Object> handleAirportNotFoundException(AirportNotFoundException ex) {
        // Create a custom error response with only the message
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),  // HTTP status code
            ex.getMessage()  // Just the error message
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ErrorResponse is a simple class to structure the response
    public static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


