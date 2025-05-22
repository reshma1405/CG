package com.capgemini.Booking.Dto;

public class Flight {

    private Long flightNumber;

    private String flightModel;
    private String carrierName;

    private Integer seatCapacity;

    private Integer availableSeats;

    private Double fares;
    private Integer scheduleId;

    // Default constructor
    public Flight() {
        // Empty constructor for JPA
    }

    // Constructor with parameters (no flightNumber here)
    public Flight(String flightModel, String carrierName, Integer seatCapacity,
                  Integer availableSeats, Double fares, Integer scheduleId) {
        this.flightModel = flightModel;
        this.carrierName = carrierName;
        this.seatCapacity = seatCapacity;
        this.availableSeats = availableSeats;
        this.fares = fares;
        this.scheduleId = scheduleId;
    }

    // Getters and Setters
    public Long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightModel() {
        return flightModel;
    }

    public void setFlightModel(String flightModel) {
        this.flightModel = flightModel;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Double getFares() {
        return fares;
    }

    public void setFares(Double fares) {
        this.fares = fares;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }
}
