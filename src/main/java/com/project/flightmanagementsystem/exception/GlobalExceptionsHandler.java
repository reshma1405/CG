



package com.project.flightmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    // ✅ Handle validation errors for @RequestBody (DTO validation errors)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    // ✅ Handle validation errors for @PathVariable and @RequestParam constraints
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        Set<jakarta.validation.ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (jakarta.validation.ConstraintViolation<?> violation : violations) {
            errorResponse.put("error", violation.getMessage());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle empty JSON request or bad request body
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleEmptyJsonException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Request body cannot be empty or null", HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle UserNotFoundException with a 404 response
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // ✅ Handle generic exceptions to prevent application crashes
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//  package com.project.flightmanagementsystem.exception;
//  
//  
//  import org.springframework.http.HttpStatus; import
//  org.springframework.http.ResponseEntity; import
//  org.springframework.validation.FieldError; import
//  org.springframework.web.bind.MethodArgumentNotValidException; import
//  org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import
//  org.springframework.web.bind.annotation.RestControllerAdvice;
//  
//  import jakarta.validation.ConstraintViolationException;
//  
//  import java.util.HashMap; import java.util.Map; import java.util.Set;
//  
//  @RestControllerAdvice
//  public class GlobalExceptionsHandler {
//  
//  
//  // Handle validation errors including @PathVariable validation
//  
//  @ExceptionHandler(ConstraintViolationException.class) public
//  ResponseEntity<Map<String, String>>
//  handleConstraintViolationException(ConstraintViolationException ex) {
//  Map<String, String> errorResponse = new HashMap<>();
//  Set<jakarta.validation.ConstraintViolation<?>> violations =
//  ((ConstraintViolationException) ex).getConstraintViolations(); for
//  (jakarta.validation.ConstraintViolation<?> violation : violations) {
//  errorResponse.put("error", violation.getMessage()); } return new
//  ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); }
//  
// 
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//      Map<String, String> errors = new HashMap<>();
//
//      for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//          errors.put(error.getField(), error.getDefaultMessage());
//      }
//
//      return errors;
//  }
//
//  
//  // Handle empty JSON request
//  
//  @ExceptionHandler(IllegalArgumentException.class) public
//  ResponseEntity<String> handleEmptyJsonException(IllegalArgumentException ex)
//  { return new ResponseEntity<>("Request body cannot be empty or null",
//  HttpStatus.BAD_REQUEST); }
//  
//  // Handle UserNotFoundException
//  
//  @ExceptionHandler(UserNotFoundException.class) public ResponseEntity<String>
//  handleUserNotFoundException(UserNotFoundException ex) { return new
//  ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); }
//  
//  // Handle generic exceptions
//  
//  @ExceptionHandler(Exception.class) public ResponseEntity<String>
//  handleGeneralExceptions(Exception ex) { return new
//  ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(),
//  HttpStatus.INTERNAL_SERVER_ERROR); } }
// 


 