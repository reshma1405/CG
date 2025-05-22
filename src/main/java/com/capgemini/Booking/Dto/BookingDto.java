package com.capgemini.Booking.Dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class BookingDto {

    private long bookingId;

    @NotNull(message = "{validation.userId.notNull}")
    @Positive(message = "{validation.userId.positive}")
    private Long userId;

    @NotNull(message = "{validation.bookingDate.notNull}")
    @FutureOrPresent(message = "{validation.bookingDate.futureOrPresent}")
    private LocalDate bookingDate;
//
//    @NotEmpty(message = "{validation.passengerList.notNull}")
//    private List<Passenger> passengerList;

    @NotNull(message = "{validation.ticketCost.notNull}")
    @Positive(message = "{validation.ticketCost.positive}")
    private double ticketCost;

    @NotNull(message = "{validation.flightNumber.notNull}")
    @Positive(message = "{validation.flightNumber.positive}")
    private Long flightNumber;

    @NotNull(message = "{validation.noOfPassengers.notNull}")
    @Min(value = 1, message = "{validation.noOfPassengers.min}")
    private int noOfPassengers;

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(double ticketCost) {
        this.ticketCost = ticketCost;
    }

//   
//    public List<Passenger> getPassengerList() {
//		return passengerList;
//	}
//
//	public void setPassengerList(List<Passenger> passengerList) {
//		this.passengerList = passengerList;
//	}

	public Long getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Long flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }
}