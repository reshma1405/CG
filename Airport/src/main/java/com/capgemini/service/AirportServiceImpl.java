package com.capgemini.service;

import com.capgemini.AirportDto.AirportDto;
import com.capgemini.entity.Airport;
import com.capgemini.exception.AirportNotFoundException;
import com.capgemini.repository.AirportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<AirportDto> viewAirport() {
        try {
            // Fetch all airports from the repository
            List<Airport> airports = airportRepository.findAll();

            // Convert the list of Airport entities to AirportDto objects
            return airports.stream()
                           .map(this::convertToDto)
                           .collect(Collectors.toList());
        } catch (PersistenceException e) {
            // Handle persistence errors (e.g., DB connection issues)
            throw new RuntimeException("Error occurred while retrieving airports from the database", e);
        } catch (Exception e) {
            // Handle any other unforeseen exceptions
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    @Override
    public AirportDto viewAirport(int airportid) {
        try {
            // Find the airport by ID
            Optional<Airport> airportOptional = airportRepository.findById(airportid);

            if (airportOptional.isEmpty()) {
                throw new AirportNotFoundException("Airport not found with ID: " + airportid);
            }

            // Convert the found Airport entity to AirportDto
            return convertToDto(airportOptional.get());
        } catch (AirportNotFoundException e) {
            // Specific exception for handling airport not found
            throw e;
        } catch (PersistenceException e) {
            // Handle database-related issues
            throw new RuntimeException("Error occurred while retrieving the airport from the database", e);
        } catch (Exception e) {
            // Handle any unforeseen exceptions
            throw new RuntimeException("An unexpected error occurred", e);
        }
    } 

    @Override
    public AirportDto createAirport(AirportDto airportDto) {
        if (airportDto == null) {
            throw new IllegalArgumentException("Airport object cannot be null");
        }

        try {
            // Convert the AirportDto to an Airport entity
            Airport airport = convertToEntity(airportDto);

            // Save the airport entity using the repository
            Airport savedAirport = airportRepository.save(airport);

            // Return the saved airport as an AirportDto
            return convertToDto(savedAirport);
        } catch (DataIntegrityViolationException e) {
            // Handle cases where data integrity violations occur (e.g., duplicate unique fields, etc.)
            throw new RuntimeException("Error occurred while saving the airport due to data integrity issues", e);
        } catch (PersistenceException e) {
            // Handle general database errors
            throw new RuntimeException("Error occurred while persisting the airport to the database", e);
        } catch (Exception e) {
            // Handle any unforeseen exceptions
            throw new RuntimeException("An unexpected error occurred while creating the airport", e);
        }
    }

    @Override
    public void deleteAirport(int airportid) {
        try {
            // Check if the airport exists by ID
            Optional<Airport> airportOptional = airportRepository.findById(airportid);
            
            if (airportOptional.isEmpty()) {
                throw new AirportNotFoundException("Airport not found with ID: " + airportid);
            }

            // Delete the airport using the repository
            airportRepository.deleteById(airportid);
        } catch (AirportNotFoundException e) {
            throw e; // Re-throw if airport is not found
        } catch (PersistenceException e) {
            throw new RuntimeException("Error occurred while deleting the airport", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting the airport", e);
        }
    }

    // Helper method to convert AirportDto to Airport entity
    private Airport convertToEntity(AirportDto airportDto) {
        Airport airport = new Airport();
        airport.setAirportid(airportDto.getAirportid());
        airport.setAirportName(airportDto.getAirportName());
        airport.setAirportLocation(airportDto.getAirportLocation());
        return airport;
    }

    // Helper method to convert Airport entity to AirportDto
    private AirportDto convertToDto(Airport airport) {
        AirportDto airportDto = new AirportDto();
        airportDto.setAirportid(airport.getAirportid());
        airportDto.setAirportName(airport.getAirportName());
        airportDto.setAirportLocation(airport.getAirportLocation());
        return airportDto;
    }
}
