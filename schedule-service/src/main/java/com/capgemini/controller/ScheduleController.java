package com.capgemini.controller;

import com.capgemini.dto.ScheduleDto;
import com.capgemini.entity.Schedule;
import com.capgemini.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);  // Logger for the controller

    @Autowired
    private ScheduleService scheduleService;

    // Endpoint to add a schedule with validation
    @PostMapping
    public ResponseEntity<Object> createSchedule(@RequestBody @Valid ScheduleDto scheduleDto) {
        logger.info("Received request to create schedule: {}", scheduleDto);

        // Convert ScheduleDto to Schedule entity
        Schedule schedule = new Schedule();
        schedule.setSourceAirportId(scheduleDto.getSourceAirportId());
        schedule.setDestinationAirportId(scheduleDto.getDestinationAirportId());
        schedule.setArrivalTime(scheduleDto.getArrivalTime());
        schedule.setDepartureTime(scheduleDto.getDepartureTime());

        try {
            // Pass the converted Schedule entity to the service
            Schedule savedSchedule = scheduleService.addSchedule(schedule);
            logger.info("Successfully created schedule with ID: {}", savedSchedule.getScheduleId());
            return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating schedule: {}", e.getMessage());
            return new ResponseEntity<>("Error creating schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all schedules
    @GetMapping
    public List<Schedule> getAllSchedules() {
        logger.info("Fetching all schedules");

        List<Schedule> schedules = scheduleService.getAllSchedules();
        logger.info("Successfully retrieved {} schedules", schedules.size());

        return schedules;
    }

    // Endpoint to get schedule details by scheduleId
    @GetMapping("/{scheduleId}")
    public Schedule getScheduleById(@PathVariable("scheduleId") int scheduleId) {
        logger.info("Fetching schedule with ID: {}", scheduleId);

        try {
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            logger.info("Successfully retrieved schedule with ID: {}", scheduleId);
            return schedule;
        } catch (Exception e) {
            logger.error("Schedule with ID {} not found: {}", scheduleId, e.getMessage());
            throw e;  // Rethrow the exception to be handled by global exception handler
        }
    }
}
