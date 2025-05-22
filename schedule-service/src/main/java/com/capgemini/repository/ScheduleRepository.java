package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entity.Schedule;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    // JpaRepository provides all necessary methods for CRUD operations, including findAll()
}
