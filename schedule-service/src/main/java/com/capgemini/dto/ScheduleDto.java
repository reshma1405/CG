package com.capgemini.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public class ScheduleDto {

    @NotNull(message = "{sourceAirportId.required}")
    @Min(value = 1, message = "{sourceAirportId.min}")
    private Integer sourceAirportId;

    @NotNull(message = "{destinationAirportId.required}") // Ensure this field is also @NotNull
    @Min(value = 1, message = "{destinationAirportId.min}")
    private Integer destinationAirportId;

    @NotNull(message = "{arrivalTime.required}")
    @Future(message = "{arrivalTime.future}")
    private LocalDateTime arrivalTime;

    @NotNull(message = "{departureTime.required}")
    @Future(message = "{departureTime.future}")
    private LocalDateTime departureTime;

    // Getters and setters
    public Integer getSourceAirportId() {
        return sourceAirportId;
    }

    public void setSourceAirportId(Integer sourceAirportId) {
        this.sourceAirportId = sourceAirportId;
    }

    public Integer getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Integer destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}
