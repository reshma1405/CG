package com.project.flightmanagementsystem.service;
 
 
import java.util.List;
 
 
import com.project.flightmanagementsystem.dto.LoginRequest;
import com.project.flightmanagementsystem.dto.UserDto;
 
import com.project.flightmanagementsystem.exception.UserNotFoundException;
 
public interface UserService {
	UserDto addUser(UserDto userDto);
    UserDto validateUser(UserDto userDto) throws UserNotFoundException;
    UserDto updateUser(UserDto userDto) throws UserNotFoundException;
    UserDto updateUserPassword(Long userId, String newPassword) throws UserNotFoundException;
	String deleteUser(Long userId) throws UserNotFoundException;
	List<UserDto> viewUser();
	UserDto viewUser(Long userId) throws UserNotFoundException;
	String login(LoginRequest loginRequest);


//	List<UserDto> getUsersByRole(String roles);
}