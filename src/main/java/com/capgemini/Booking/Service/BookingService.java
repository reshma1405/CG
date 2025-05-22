package com.capgemini.Booking.Service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.Booking.Dto.BookingDto;
import com.capgemini.Booking.Exception.BookingNotFoundException;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto);
    void cancelBooking(Long bookingId) throws BookingNotFoundException;
    BookingDto viewBooking(Long bookingId) throws BookingNotFoundException;
    List<BookingDto> viewBookingList(LocalDate bookingDate);
    List<BookingDto> viewBookingList(Long flightNumber);
    List<BookingDto> viewBookingHistory(Long userId);
    List<BookingDto> getAllBookings();
    BookingDto updateBooking(BookingDto bookingDto);
    
}
