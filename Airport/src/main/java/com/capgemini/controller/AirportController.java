package com.capgemini.controller;

import com.capgemini.AirportDto.AirportDto;
import com.capgemini.service.AirportService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;
    
    @PostMapping
    public ResponseEntity<Object> createAirport(@Valid @RequestBody AirportDto airportDto, BindingResult result) {
        if (result.hasErrors()) {
            // Collect validation errors
            Map<String, String> errorMessages = new HashMap<>();

            // Collecting each error message and adding it to the map
            result.getAllErrors().forEach(error -> {
                String fieldName = ((org.springframework.validation.FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });

            // Returning a structured error response with a BAD_REQUEST status
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // If no validation errors, create the airport
        AirportDto createdAirport = airportService.createAirport(airportDto);

        // Returning the created airport with a CREATED status
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    // get all airports
    @GetMapping
    public List<AirportDto> getAllAirports() {
        return airportService.viewAirport();  // Call service to get all airports
    }

    // get an airport by its ID
    @GetMapping("/id/{airportid}")
    public AirportDto getAirportById(@PathVariable("airportid") int airportid) {
        return airportService.viewAirport(airportid);  // Call service to get airport by ID
    }
    
    @DeleteMapping("/id/{airportid}")
    public String deleteAirport(@PathVariable("airportid") int airportid) {
        airportService.deleteAirport(airportid);  // Call service to delete airport by ID
        return "Airport with ID " + airportid + " has been deleted.";  // Return a confirmation message
    }
}
