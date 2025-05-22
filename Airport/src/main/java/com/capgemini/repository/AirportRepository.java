package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entity.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    Airport findByAirportid(int airportid);  // Custom query method to find by airport code (id)

}
