package com.capgemini.Controller;

import com.capgemini.Dto.FlightDTO;
import com.capgemini.Entity.Flight;
import com.capgemini.Exception.FlightNotFoundException;
import com.capgemini.Exception.ScheduleNotFoundException;
import com.capgemini.Service.IFlightService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    // Add a new flight
    @PostMapping("/add")
    public ResponseEntity<Object> addFlight(@Valid @RequestBody FlightDTO flightDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect all validation error messages
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            });

            // Return validation errors as a list of messages
            return new ResponseEntity<>(errorMessages.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            Flight flight = flightService.addFlight(flightDTO);
            return new ResponseEntity<>(flight, HttpStatus.CREATED);
        } catch (ScheduleNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Return error if scheduleId is invalid
        }
    }

    // Modify an existing flight
    @PutMapping("/modify/{flightNumber}")
    public ResponseEntity<Flight> modifyFlight(@PathVariable Long flightNumber, @RequestBody FlightDTO flightDTO) {
        try {
            Flight flight = flightService.modifyFlight(flightNumber, flightDTO);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // View a specific flight by flight number
 // View a specific flight by flight number
    @GetMapping("/{flightNumber}")
    public ResponseEntity<Flight> viewFlight(@PathVariable Long flightNumber) {
        try {
            Optional<Flight> flight = flightService.viewFlight(flightNumber);
            
            // If flight is present, return it, otherwise return NOT_FOUND status
            return flight.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return 404 if not found
            
        } catch (FlightNotFoundException e) {
            // Return 404 with error message if exception occurs
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // View all available flights
    @GetMapping("/all")
    public ResponseEntity<List<Flight>> viewFlights() {
        try {
            List<Flight> flights = flightService.viewFlights();
            if (flights.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no flights exist
            }
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a flight by flight number
    @DeleteMapping("/delete/{flightNumber}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightNumber) {
        try {
            boolean deleted = flightService.deleteFlight(flightNumber);
            if (deleted) {
                return new ResponseEntity<>("Flight deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight not found.", HttpStatus.NOT_FOUND);
            }
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Validate flight details
    @PostMapping("/validate")
    public ResponseEntity<String> validateFlight(@RequestBody FlightDTO flightDTO) {
        boolean isValid = flightService.validateFlight(flightDTO);
        return new ResponseEntity<>(isValid ? "Flight is valid." : "Flight is invalid.", HttpStatus.OK);
    }
}
