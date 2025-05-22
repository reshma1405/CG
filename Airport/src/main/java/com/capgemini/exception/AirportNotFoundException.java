package com.capgemini.exception;

public class AirportNotFoundException extends RuntimeException {
    // Constructor with a message
    public AirportNotFoundException(String message) {
        super(message);  // Calls the parent constructor to pass the error message
    }

    // Constructor with a message and cause (for nested exceptions)
    public AirportNotFoundException(String message, Throwable cause) {
        super(message, cause);  // Calls the parent constructor to pass the error message and cause
    }
}
