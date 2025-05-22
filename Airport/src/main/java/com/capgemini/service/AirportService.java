package com.capgemini.service;

import java.util.List;
import com.capgemini.AirportDto.AirportDto;

public interface AirportService {

    // Method to fetch all airports
    List<AirportDto> viewAirport();
    
    // Method to fetch an airport by its ID
    AirportDto viewAirport(int airportid);
    
    // Method to create a new airport
    AirportDto createAirport(AirportDto airport);
    
    // Method to delete an airport by its ID
    void deleteAirport(int airportid);
}
