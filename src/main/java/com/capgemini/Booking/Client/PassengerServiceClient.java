//package com.capgemini.Booking.Client;
//
//import com.capgemini.Booking.Dto.Passenger;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//// Define the Feign Client interface
//@FeignClient(name = "passenger-service", url = "http://localhost:9085/passengers")
//public interface PassengerServiceClient {
//
//	// Fetch all passengers
//    @GetMapping("/passengers")
//    List<Passenger> getAllPassengers();
//}
