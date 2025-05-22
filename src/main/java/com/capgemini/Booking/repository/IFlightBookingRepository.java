package com.capgemini.Booking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.Booking.entity.Booking;

@Repository
public interface IFlightBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingDate(LocalDate bookingDate);
    List<Booking> findByFlightNumber(Long flightNumber);
    List<Booking> findByUserId(Long userId);
}
