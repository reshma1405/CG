package com.capgemini.Exception;


public class FlightNotFoundException extends Exception {  // Extend from Exception

    // Constructor that accepts a custom error message
    public FlightNotFoundException(String message) {
        super(message);  // Pass the message to the superclass (Exception)
    }
}
