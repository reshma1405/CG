package com.capgemini.service;

import com.capgemini.dto.Airport;
import com.capgemini.entity.Schedule;
import com.capgemini.feignclient.AirportFeignClient;
import com.capgemini.repository.ScheduleRepository;
import com.capgemini.exception.InvalidAirportIdException; 
import com.capgemini.exception.ScheduleNotFoundException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import feign.FeignException;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);  // Create a logger instance for this service

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AirportFeignClient airportFeignClient;

    // Method to create a schedule
    public Schedule addSchedule(Schedule schedule) {
        logger.info("Attempting to add schedule for source airport ID: {} and destination airport ID: {}", schedule.getSourceAirportId(), schedule.getDestinationAirportId());
        
        try {
            // Fetch the source and destination airports using the airport service via Feign client
            Airport sourceAirport = airportFeignClient.getAirportById(schedule.getSourceAirportId());
            Airport destinationAirport = airportFeignClient.getAirportById(schedule.getDestinationAirportId());

            logger.info("Successfully retrieved source airport: {} and destination airport: {}", sourceAirport, destinationAirport);


            // Save the schedule to your repository
            Schedule savedSchedule = scheduleRepository.save(schedule);
            logger.info("Schedule successfully saved with ID: {}", savedSchedule.getScheduleId());

            return savedSchedule;

        } catch (FeignException.NotFound e) {
            logger.error("Airport not found with provided ID", e);
            // Handle the case when an airport is not found (e.g., invalid airport ID)
            throw new InvalidAirportIdException("Invalid airport ID provided. Please check source or destination airport ID.");
        } catch (Exception e) {
            logger.error("An error occurred while creating the schedule", e);
            // Handle other unexpected exceptions
            throw new RuntimeException("An error occurred while creating the schedule: " + e.getMessage(), e);
        }
    }

    // Method to get all schedules
    public List<Schedule> getAllSchedules() {
        logger.info("Fetching all schedules from the repository");
        List<Schedule> schedules = scheduleRepository.findAll();
        logger.info("Successfully retrieved {} schedules", schedules.size());
        return schedules;
    }

    // Method to get airport details using the Feign Client
    public Airport getAirportDetails(int airportId) {
        logger.info("Fetching details for airport ID: {}", airportId);
        Airport airport = airportFeignClient.getAirportById(airportId);  // Fetch airport details using Feign Client
        logger.info("Successfully retrieved airport details: {}", airport);
        return airport;
    }
    
    // Method to get schedule by ID
    public Schedule getScheduleById(int scheduleId) {
        logger.info("Fetching schedule with ID: {}", scheduleId);
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);

        if (schedule.isPresent()) {
            logger.info("Successfully retrieved schedule with ID: {}", scheduleId);
            return schedule.get();
        } else {
            logger.warn("Schedule with ID: {} not found", scheduleId);
            throw new ScheduleNotFoundException("Schedule with ID " + scheduleId + " not found.");
        }
    }
}
