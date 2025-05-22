package com.capgemini.FeignClient;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.Dto.Schedule;  // Ensure this is the correct DTO used by your flight service

// Feign client to connect to the Schedule service
@FeignClient(name = "schedule-service", url = "http://localhost:8082/schedule")  // Replace URL if needed
public interface ScheduleFeignClient {

    // Get schedule by scheduleId
    @GetMapping("/{id}")  // Match the path used in ScheduleController
    Schedule getScheduleById(@PathVariable("id") int id);  // Ensure the variable name matches the path

    // Get all schedules
    @GetMapping
    List<Schedule> getAllSchedules();
}
