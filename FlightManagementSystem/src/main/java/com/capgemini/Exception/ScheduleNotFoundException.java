package com.capgemini.Exception;

public class ScheduleNotFoundException extends RuntimeException {

    // Constructor that accepts a message
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
