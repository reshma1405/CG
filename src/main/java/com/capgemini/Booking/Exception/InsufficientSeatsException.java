package com.capgemini.Booking.Exception;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(String message) {

        super(message);
    }
}
