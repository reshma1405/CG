package com.capgemini;

import com.capgemini.dto.Airport;
import com.capgemini.dto.ScheduleDto;
import com.capgemini.entity.Schedule;
import com.capgemini.feignclient.AirportFeignClient;
import com.capgemini.exception.ScheduleNotFoundException;
import com.capgemini.repository.ScheduleRepository;
import com.capgemini.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleServiceApplicationTests {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private AirportFeignClient airportFeignClient;

    private ScheduleDto scheduleDto;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize ScheduleDto
        scheduleDto = new ScheduleDto();
        scheduleDto.setSourceAirportId(1);
        scheduleDto.setDestinationAirportId(2);
        scheduleDto.setArrivalTime(LocalDateTime.now().plusHours(1));
        scheduleDto.setDepartureTime(LocalDateTime.now().plusHours(2));

        // Initialize Schedule entity (to be used in mock behavior)
        schedule = new Schedule();
        schedule.setScheduleId(1);
        schedule.setSourceAirportId(1);
        schedule.setDestinationAirportId(2);
        schedule.setArrivalTime(scheduleDto.getArrivalTime());
        schedule.setDepartureTime(scheduleDto.getDepartureTime());
    }

    @Test
    void contextLoads() {
        // This is a basic test to ensure the application context loads correctly.
    }

    @Test
    void testAddSchedule() {
        // Mock the behavior of the Feign client
        Airport sourceAirport = new Airport();
        sourceAirport.setAirportid(1);
        sourceAirport.setAirportName("Source Airport");
        sourceAirport.setAirportLocation("Delhi");

        Airport destinationAirport = new Airport();
        destinationAirport.setAirportid(2);
        destinationAirport.setAirportName("Destination Airport");
        destinationAirport.setAirportLocation("Mumbai");

        // Mock the behavior of the Feign client to return the mock airports
        when(airportFeignClient.getAirportById(1)).thenReturn(sourceAirport);
        when(airportFeignClient.getAirportById(2)).thenReturn(destinationAirport);

        // Mock the repository save method
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Call the addSchedule method
        Schedule result = scheduleService.addSchedule(schedule);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getScheduleId());
        assertEquals(1, result.getSourceAirportId());
        assertEquals(2, result.getDestinationAirportId());
    }



    @Test
    void testGetAllSchedules() {
        // Mock the repository to return a list of schedules
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        // Call the getAllSchedules method
        List<Schedule> result = scheduleService.getAllSchedules();

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getScheduleId());
    }

    @Test
    void testGetScheduleByIdFound() {
        // Mock the repository to return a schedule by ID
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));

        // Call the getScheduleById method
        Schedule result = scheduleService.getScheduleById(1);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getScheduleId());
    }

    @Test
    void testGetScheduleByIdNotFound() {
        // Mock the repository to return an empty Optional for schedule ID 1
        when(scheduleRepository.findById(1)).thenReturn(Optional.empty());

        // Call the getScheduleById method and expect the exception
        ScheduleNotFoundException exception = assertThrows(ScheduleNotFoundException.class, () -> {
            scheduleService.getScheduleById(1);
        });

        assertEquals("Schedule with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testGetAirportDetails() {
        // Mock the behavior of the Feign client
        Airport airport = new Airport();
        airport.setAirportid(1);  // Using the correct setter
        airport.setAirportName("Test Airport");
        airport.setAirportLocation("Delhi");

        when(airportFeignClient.getAirportById(1)).thenReturn(airport);

        // Call the getAirportDetails method
        Airport result = scheduleService.getAirportDetails(1);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getAirportid());  // Using correct getter
        assertEquals("Test Airport", result.getAirportName());
        assertEquals("Delhi", result.getAirportLocation());
    }
}
