//package com.capgemini.Booking.Client;

//
//	import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//	import org.springframework.web.bind.annotation.PathVariable;
//
//import com.capgemini.Booking.Dto.Flight;
//@FeignClient(name = "flight-service", url = "http://localhost:8080")
//public interface FlightServiceClient {
//
//
//	    @GetMapping("/flights/{id}")
//	    Flight getFlightById(@PathVariable("id") Long id);
//	}
//
package com.capgemini.Booking.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.Booking.Dto.Flight;

@FeignClient(name = "flight-service",url = "http://localhost:8080/api/flights")
public interface FlightServiceClient {

    @GetMapping("/{flightNumber}")
    Flight getFlightByNumber(@PathVariable("flightNumber") Long flightNumber);
}