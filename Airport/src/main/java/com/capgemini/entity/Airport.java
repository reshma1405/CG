package com.capgemini.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Airport")
public class Airport {
	@Id
	@Column(name="airport_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int airportid;
	@Column(name="airport_name")
	private String airportName;
	@Column(name="airport_location")
	private String airportLocation;
	
	public int getAirportid() {
		return airportid;
	}
	public void setAirportid(int airportid) {
		this.airportid = airportid;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public String getAirportLocation() {
		return airportLocation;
	}
	public void setAirportLocation(String airportLocation) {
		this.airportLocation = airportLocation;
	}
	
}

