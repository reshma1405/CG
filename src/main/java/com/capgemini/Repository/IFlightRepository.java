package com.capgemini.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.Entity.Flight;

@Repository
public interface IFlightRepository extends JpaRepository<Flight, Long> {
    // Custom query methods can be added here if necessary

    // Example: Find flights by carrier name
    // List<Flight> findByCarrierName(String carrierName);
}

