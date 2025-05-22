//package com.project.flightmanagementsystem;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class UserApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}
package com.project.flightmanagementsystem;

import com.project.flightmanagementsystem.dto.LoginRequest;
import com.project.flightmanagementsystem.dto.UserDto;
import com.project.flightmanagementsystem.exception.UserNotFoundException;
import com.project.flightmanagementsystem.service.UserServiceImpl;



import com.project.flightmanagementsystem.repository.IUserRepository;
import com.project.flightmanagementsystem.jwt.JwtUtil;
import com.project.flightmanagementsystem.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserApplicationTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserDto userDto;
    private User user;


    @BeforeEach
    void setUp() {
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");  // Add role to the set
        userDto = new UserDto(1L, "testuser", "password", 1234567890L, "test@example.com", roles);  // Updated userDto with Set<String> for roles
        user = new User(1L, "testuser", "password", 1234567890L, "test@example.com", roles);  // User with a String for roles
    }


    @Test
    void testAddUser() {
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUser = userService.addUser(userDto);

        assertNotNull(savedUser);
        assertEquals(savedUser.getUsername(), userDto.getUsername());
        assertEquals(savedUser.getEmail(), userDto.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testValidateUser_Success() throws UserNotFoundException {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq(userDto.getPassword()), eq(user.getPassword()))).thenReturn(true);

        UserDto validatedUser = userService.validateUser(userDto);

        assertNotNull(validatedUser);
        assertEquals(validatedUser.getUsername(), userDto.getUsername());
        verify(userRepository).findById(userDto.getUserId());
    }

    @Test
    void testValidateUser_Failure() {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.validateUser(userDto));
    }

    @Test
    void testUpdateUser_Success() throws UserNotFoundException {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userDto.setUsername("updatedUsername");
        UserDto updatedUser = userService.updateUser(userDto);

        assertEquals("updatedUsername", updatedUser.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_Failure() {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));
    }

    @Test
    void testUpdateUserPassword_Success() throws UserNotFoundException {
        // Mock the password encoding
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        // Mock user repository behavior
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the method to update the password
        UserDto updatedPasswordUser = userService.updateUserPassword(userDto.getUserId(), "newPassword");

        // Assertions
        assertNotNull(updatedPasswordUser);
        assertEquals("encodedNewPassword", updatedPasswordUser.getPassword());  // Check the encoded password
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserPassword_Failure() {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserPassword(userDto.getUserId(), "newPassword"));
    }

    @Test
    void testViewUser_Success() throws UserNotFoundException {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));

        UserDto foundUser = userService.viewUser(userDto.getUserId());

        assertNotNull(foundUser);
        assertEquals(userDto.getUsername(), foundUser.getUsername());
    }

    @Test
    void testViewUser_Failure() {
        when(userRepository.findById(userDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.viewUser(userDto.getUserId()));
    }

    @Test
    void testViewAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> users = userService.viewUser();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getUsername(), users.get(0).getUsername());
    }

    @Test
    void testDeleteUser_Success() throws UserNotFoundException {
        when(userRepository.existsById(userDto.getUserId())).thenReturn(true);

        String result = userService.deleteUser(userDto.getUserId());

        assertEquals("User deleted successfully", result);
        verify(userRepository).deleteById(userDto.getUserId());
    }

    @Test
    void testDeleteUser_Failure() {
        when(userRepository.existsById(userDto.getUserId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userDto.getUserId()));
    }

    @Test
    void testLogin() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.generateToken(loginRequest.username())).thenReturn("jwtToken");

        String token = userService.login(loginRequest);

        assertNotNull(token);
        assertEquals("jwtToken", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

}
