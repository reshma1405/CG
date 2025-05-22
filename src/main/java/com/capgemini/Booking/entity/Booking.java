package com.capgemini.Booking.entity;

import java.time.LocalDate;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long bookingId;
    
    private long userId;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
    

    @Column(name = "ticket_cost", nullable = false)
    private double ticketCost;

 
   @Column(name = "flightNumber", nullable = false)
    private Long flightNumber; 
   
  // private List <Passenger>passengerList;

    @Column(name = "no_of_passengers", nullable = false)
    private int noOfPassengers;
    // Getters and Setters
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	
	public Long getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Long flightNumber) {
		this.flightNumber = flightNumber;
	}
//
//	public List<Passenger> getPassengerList() {
//		return passengerList;
//	}
//
//	public void setPassengerList(List<Passenger> passengerList) {
//		this.passengerList = passengerList;
//	}

	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}
}