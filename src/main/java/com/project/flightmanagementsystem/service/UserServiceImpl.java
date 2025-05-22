package com.project.flightmanagementsystem.service;

import com.project.flightmanagementsystem.dto.LoginRequest;
import com.project.flightmanagementsystem.dto.UserDto;
import com.project.flightmanagementsystem.entity.User;
import com.project.flightmanagementsystem.exception.UserNotFoundException;
import com.project.flightmanagementsystem.jwt.JwtUtil;
import com.project.flightmanagementsystem.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        logger.info("Adding new user: {}", userDto.getUsername());
        User user = mapToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = userRepository.save(user);
        logger.info("User added successfully: {}", newUser.getUsername());
        return mapToDto(newUser);
    }

    @Override
    public UserDto validateUser(UserDto userDto) throws UserNotFoundException {
        logger.info("Validating user: {}", userDto.getUsername());
        Optional<User> foundUser = userRepository.findById(userDto.getUserId());
        if (foundUser.isPresent() && passwordEncoder.matches(userDto.getPassword(), foundUser.get().getPassword())) {
            logger.info("User validated successfully: {}", foundUser.get().getUsername());
            return mapToDto(foundUser.get());
        }
        logger.error("Invalid credentials for user: {}", userDto.getUsername());
        throw new UserNotFoundException("Invalid user credentials");
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user: {}", userDto.getUsername());
        User existingUser = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userDto.getUserId());
                    return new UserNotFoundException("User not found");
                });

        if (userDto.getUsername() != null) existingUser.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null) existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getUserPhone() != null) existingUser.setUserPhone(userDto.getUserPhone());
        if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());
        if (userDto.getRoles() != null) existingUser.setRoles(userDto.getRoles());

        User updatedUser = userRepository.save(existingUser);
        logger.info("User updated successfully: {}", updatedUser.getUsername());
        return mapToDto(updatedUser);
    }

    @Override
    public UserDto updateUserPassword(Long userId, String newPassword) throws UserNotFoundException {
        logger.info("Updating password for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userId);
                    return new UserNotFoundException("User not found");
                });

        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(user);
        logger.info("Password updated successfully for user: {}", updatedUser.getUsername());
        return mapToDto(updatedUser);
    }

    @Override
    public UserDto viewUser(Long userId) throws UserNotFoundException {
        logger.info("Viewing user with ID: {}", userId);
        return userRepository.findById(userId)
                .map(this::mapToDto)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userId);
                    return new UserNotFoundException("User not found");
                });
    }

    @Override
    public List<UserDto> viewUser() {
        logger.info("Fetching all users.");
        List<UserDto> users = userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} users.", users.size());
        return users;
    }

    @Override
    public String deleteUser(Long userId) throws UserNotFoundException {
        logger.info("Deleting user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            logger.error("User not found: {}", userId);
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
        logger.info("User deleted successfully: {}", userId);
        return "User deleted successfully";
    }

    @Override
    public String login(LoginRequest loginRequest) {
        logger.info("Attempting login for user: {}", loginRequest.username());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()));
        String token = jwtUtil.generateToken(loginRequest.username());
        logger.info("User logged in successfully: {}", loginRequest.username());
        return token;
    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.getUserId(), user.getUsername(),
                user.getPassword(), user.getUserPhone(), user.getEmail(), user.getRoles());
    }

    private User mapToEntity(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getUsername(),
                userDto.getPassword(), userDto.getUserPhone(), userDto.getEmail(), userDto.getRoles());
    }
}

 