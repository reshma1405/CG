package com.capgemini.Dto;

import java.time.LocalDateTime;

public class Schedule {
	private int scheduleId;
	 
   
    private String AirportName;
 
   
    private String airportLocation;
 
    
    private LocalDateTime arrivalTime;
 
   
    private LocalDateTime departureTime;


	public int getScheduleId() {
		return scheduleId;
	}


	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}


	public String getAirportName() {
		return AirportName;
	}


	public void setAirportName(String airportName) {
		AirportName = airportName;
	}


	public String getAirportLocation() {
		return airportLocation;
	}


	public void setAirportLocation(String airportLocation) {
		this.airportLocation = airportLocation;
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

