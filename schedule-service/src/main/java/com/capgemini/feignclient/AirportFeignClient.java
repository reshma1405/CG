package com.capgemini.feignclient;

import com.capgemini.dto.Airport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "airport-service", url = "http://localhost:8080/api/airports")
public interface AirportFeignClient {

    @GetMapping("/id/{id}")  // Correct path to get airport by ID
    Airport getAirportById(@PathVariable("id") int airportId);

    // Adding the getAirportDetails method to fetch the airport details by id
    @GetMapping("/details/{id}")
    Airport getAirportDetails(@PathVariable("id") int airportId);
}
