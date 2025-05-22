package com.capgemini.Booking.Controller;
import com.capgemini.Booking.Dto.BookingDto;


import com.capgemini.Booking.Service.BookingService;
import com.capgemini.Booking.Exception.BookingNotFoundException;
import com.capgemini.Booking.Exception.FlightNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
   
//    @PostMapping("/create")
//    public ResponseEntity<BookingDto> addBooking(@Valid @RequestBody BookingDto bookingDto) {
//        BookingDto newBooking = bookingService.addBooking(bookingDto);
//        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
//    }
    

    @PostMapping("/create")
    public ResponseEntity<?> addBooking(@RequestBody BookingDto bookingDto) {
        try {
            BookingDto newBooking = bookingService.addBooking(bookingDto);
            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> viewBooking(@PathVariable Long bookingId) {
        try {
            BookingDto bookingDto = bookingService.viewBooking(bookingId);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/date/{bookingDate}")
    public ResponseEntity<List<BookingDto>> viewBookingList(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate) {
        List<BookingDto> bookings = bookingService.viewBookingList(bookingDate);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/flight/{flightNumber}")
    public ResponseEntity<List<BookingDto>> viewBookingListByFlight(@PathVariable Long flightNumber) {
        List<BookingDto> bookings = bookingService.viewBookingList(flightNumber);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> viewBookingHistory(@PathVariable Long userId) {
        List<BookingDto> bookings = bookingService.viewBookingHistory(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @PutMapping("/update/{bookingId}")
    public ResponseEntity<BookingDto> updateBooking(@RequestBody BookingDto bookingDto) {
        BookingDto updatedBooking = bookingService.updateBooking(bookingDto);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}

