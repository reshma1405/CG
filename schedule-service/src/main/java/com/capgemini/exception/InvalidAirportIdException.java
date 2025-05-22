package com.capgemini.exception;

public class InvalidAirportIdException extends RuntimeException {
    public InvalidAirportIdException(String message) {
        super(message);
    }
}
