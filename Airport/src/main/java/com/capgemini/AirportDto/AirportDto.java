package com.capgemini.AirportDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AirportDto {

    private int airportid;

    @NotNull(message = "{airport.nameRequired}")
    @Size(min = 3, max = 100, message = "{airport.name}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{airport.name.alphabetsOnly}")
    private String airportName;

    @NotNull(message = "{airport.locationRequired}")
    @Size(min = 3, max = 100, message = "{airport.location}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{airport.location.alphabetsOnly}")
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
