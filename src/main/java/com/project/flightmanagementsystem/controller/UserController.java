package com.project.flightmanagementsystem.controller;

import com.project.flightmanagementsystem.dto.LoginRequest;
import com.project.flightmanagementsystem.dto.UserDto;
import com.project.flightmanagementsystem.exception.UserNotFoundException;
import com.project.flightmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Received request to add user: {}", userDto.getUsername());
        UserDto createdUser = userService.addUser(userDto);
        logger.info("User {} created successfully with ID {}", createdUser.getUsername(), createdUser.getUserId());
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        logger.info("Received login request for username: {}", loginRequest.username());
        String token = userService.login(loginRequest);
        logger.info("Login successful for username: {}", loginRequest.username());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<UserDto> validateUser(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        logger.info("Validating user with ID: {}", userDto.getUserId());
        UserDto validatedUser = userService.validateUser(userDto);
        logger.info("User with ID {} validated successfully", userDto.getUserId());
        return ResponseEntity.ok(validatedUser);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user with ID: {}", userDto.getUserId());
        UserDto updatedUser = userService.updateUser(userDto);
        logger.info("User with ID {} updated successfully", userDto.getUserId());
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/password")
    public ResponseEntity<UserDto> updateUserPassword(@RequestBody Map<String, String> request) throws UserNotFoundException {
        Long userId = Long.parseLong(request.get("userId"));
        String newPassword = request.get("newPassword");
        logger.info("Received password update request for user ID: {}", userId);
        UserDto updatedUser = userService.updateUserPassword(userId, newPassword);
        logger.info("Password updated successfully for user ID: {}", userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<UserDto> viewUser(@PathVariable @Min(value = 1, message = "UserId should be positive") Long id)
            throws UserNotFoundException {
        logger.info("Fetching details for user with ID: {}", id);
        UserDto userDto = userService.viewUser(id);
        logger.info("User with ID {} fetched successfully", id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/view")
    public ResponseEntity<List<UserDto>> viewUser() {
        logger.info("Fetching all users");
        List<UserDto> users = userService.viewUser();
        logger.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        logger.info("Received request to delete user with ID: {}", id);
        String msg = userService.deleteUser(id);
        logger.info("User with ID {} deleted successfully", id);
        return ResponseEntity.ok(msg);
    }

    @GetMapping
    public String welcome() {
        logger.info("Welcome endpoint accessed");
        return "Welcome to Authentication Service";
    }
}
