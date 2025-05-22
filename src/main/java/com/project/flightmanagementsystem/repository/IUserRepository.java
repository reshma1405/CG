package com.project.flightmanagementsystem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.flightmanagementsystem.entity.User;


@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByUsername(String username);

//	Optional<User> findByRole(String upperCase);

	
	

}
