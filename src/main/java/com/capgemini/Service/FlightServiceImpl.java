package com.capgemini.Service;

import com.capgemini.Dto.FlightDTO;
import com.capgemini.Entity.Flight;
import com.capgemini.Exception.FlightNotFoundException;
import com.capgemini.Exception.ScheduleNotFoundException;
import com.capgemini.FeignClient.ScheduleFeignClient;
import com.capgemini.Repository.IFlightRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private Validator validator;  // For validating the DTO

    @Autowired
    private ScheduleFeignClient scheduleFeignClient;  // Injecting Feign Client

    @Override
    public Flight addFlight(FlightDTO flightDTO) {
        // Validate the FlightDTO
        Set<ConstraintViolation<FlightDTO>> violations = validator.validate(flightDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed for flight: " + violations);
        }

        // Fetch schedule details using Feign client
        try {
            scheduleFeignClient.getScheduleById(flightDTO.getScheduleId());  // Fetch schedule by scheduleId
        } catch (Exception e) {
            throw new ScheduleNotFoundException("Schedule with ID " + flightDTO.getScheduleId() + " not found.");
        }

        // If the schedule is found, create and save the flight
        Flight flight = new Flight();
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setFlightModel(flightDTO.getFlightModel());
        flight.setCarrierName(flightDTO.getCarrierName());
        flight.setSeatCapacity(flightDTO.getSeatCapacity());
        flight.setAvailableSeats(flightDTO.getAvailableSeats());
        flight.setFares(flightDTO.getFares());
        flight.setScheduleId(flightDTO.getScheduleId());

        return flightRepository.save(flight);
    }

    @Override
    public Flight modifyFlight(Long flightNumber, FlightDTO flightDTO) throws FlightNotFoundException {
        // Validate the FlightDTO
        Set<ConstraintViolation<FlightDTO>> violations = validator.validate(flightDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed for flight: " + violations);
        }

        // Find the existing flight by flightNumber
        Flight flight = flightRepository.findById(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight with number " + flightNumber + " not found."));

        // Fetch schedule details for the new scheduleId using Feign client
        try {
            scheduleFeignClient.getScheduleById(flightDTO.getScheduleId());  // Validate scheduleId
        } catch (Exception e) {
            throw new ScheduleNotFoundException("Schedule with ID " + flightDTO.getScheduleId() + " not found.");
        }

        // Modify the flight details
        flight.setFlightModel(flightDTO.getFlightModel());
        flight.setCarrierName(flightDTO.getCarrierName());
        flight.setSeatCapacity(flightDTO.getSeatCapacity());
        flight.setAvailableSeats(flightDTO.getAvailableSeats());
        flight.setFares(flightDTO.getFares());
        flight.setScheduleId(flightDTO.getScheduleId());

        return flightRepository.save(flight);
    }

    @Override
    public Optional<Flight> viewFlight(Long flightNumber) throws FlightNotFoundException {
        return Optional.ofNullable(flightRepository.findById(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight with number " + flightNumber + " not found.")));
    }

    @Override
    public List<Flight> viewFlights() throws FlightNotFoundException {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            throw new FlightNotFoundException("No flights available.");
        }
        return flights;
    }

    @Override
    public boolean deleteFlight(Long flightNumber) throws FlightNotFoundException {
        Flight flight = flightRepository.findById(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight with number " + flightNumber + " not found."));

        flightRepository.delete(flight);
        return true;
    }

    @Override
    public boolean validateFlight(FlightDTO flightDTO) {
        // Use Jakarta Validation API to validate the DTO
        Set<ConstraintViolation<FlightDTO>> violations = validator.validate(flightDTO);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<FlightDTO> violation : violations) {
                System.out.println(violation.getMessage());  // You can log the violations instead
            }
            return false;
        }
        return true;
    }
}
