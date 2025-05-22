//package com.capgemini.Booking.Service;


//import java.time.LocalDate;
//
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.capgemini.Booking.Client.FlightServiceClient;
////import com.capgemini.Booking.Client.UserServiceClient;
//
//import com.capgemini.Booking.Dto.BookingDto;
//import com.capgemini.Booking.Dto.Flight;
////import com.capgemini.Booking.Dto.User;
//import com.capgemini.Booking.Exception.BookingNotFoundException;
//import com.capgemini.Booking.entity.Booking;
//import com.capgemini.Booking.repository.IFlightBookingRepository;
//
//@Service
//public class BookingServiceImpl implements BookingService {
//
//    @Autowired
//    private IFlightBookingRepository bookingRepository;
//    @Autowired
//    private FlightServiceClient FlightServiceClient;
//    
//   // @Autowired
//   // private UserServiceClient userServiceClient;
//
//    @Override
//    public BookingDto addBooking(BookingDto bookingDto) {
//        Booking booking = convertToEntity(bookingDto);
//        Booking savedBooking = bookingRepository.save(booking);
//        return convertToDto(savedBooking);
//    }
//
//    @Override
//    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
//        Optional<Booking> booking = bookingRepository.findById(bookingId);
//        if (booking.isPresent()) {
//            bookingRepository.delete(booking.get());
//        } else {
//            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
//        }
//    }
//
//    @Override
//    public BookingDto viewBooking(Long bookingId) throws BookingNotFoundException {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
//        return convertToDto(booking);
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(LocalDate bookingDate) {
//        List<Booking> bookings = bookingRepository.findByBookingDate(bookingDate);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(Long flightNumber) {
//        List<Booking> bookings = bookingRepository.findByFlightNumber(flightNumber);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingHistory(Long userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public BookingDto updateBooking(BookingDto bookingDto) {
//        Booking booking = convertToEntity(bookingDto);
//        Booking updatedBooking = bookingRepository.save(booking);
//        return convertToDto(updatedBooking);
//    }
//    
//    @Override
//    public List<BookingDto> getAllBookings() {
//        List<Booking> bookings = bookingRepository.findAll();
//        if (bookings.isEmpty()) {
//            throw new BookingNotFoundException("No bookings found in the database");
//        }
//        return bookings.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private Booking convertToEntity(BookingDto bookingDto) {
//        Booking booking = new Booking();
//        booking.setBookingId(bookingDto.getBookingId());
//        booking.setUserId(bookingDto.getUserId());
//        booking.setBookingDate(bookingDto.getBookingDate());
//        booking.setTicketCost(bookingDto.getTicketCost());
//        booking.setFlightNumber(bookingDto.getFlightNumber());
//        booking.setNoOfPassengers(bookingDto.getNoOfPassengers()); // Corrected typo here
//        return booking;
//    }
//
//    private BookingDto convertToDto(Booking booking) {
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setBookingId(booking.getBookingId());
//        bookingDto.setUserId(booking.getUserId());
//        bookingDto.setBookingDate(booking.getBookingDate());
//        bookingDto.setTicketCost(booking.getTicketCost());
//        bookingDto.setFlightNumber(booking.getFlightNumber());
//        bookingDto.setNoOfPassengers(booking.getNoOfPassengers());
//        return bookingDto;
//    }
//
//    public Flight getFlightDetails(Long flightNumber) {
//        return FlightServiceClient.getFlightByNumber(flightNumber);
//    }
////    public User getUserDetails(Long userId) {
////        return userServiceClient.getUserById(userId);
////    }
//
//}


//
//package com.capgemini.Booking.Service;
//
//import java.time.LocalDate;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.capgemini.Booking.Client.FlightServiceClient;
//import com.capgemini.Booking.Client.UserServiceClient;
//import com.capgemini.Booking.Dto.BookingDto;
//import com.capgemini.Booking.Dto.Flight;
//import com.capgemini.Booking.Exception.BookingNotFoundException;
//import com.capgemini.Booking.Exception.FlightNotFoundException;
//import com.capgemini.Booking.Exception.UserNotFoundException;
//import com.capgemini.Booking.entity.Booking;
//import com.capgemini.Booking.repository.IFlightBookingRepository;
//import feign.FeignException;
//
//@Service
//public class BookingServiceImpl implements BookingService {
//
//    @Autowired
//    private IFlightBookingRepository bookingRepository;
//    
//    @Autowired
//    private FlightServiceClient flightServiceClient;
//    
//    @Autowired
//    private UserServiceClient userServiceClient; // Uncomment and use if needed for user details
//
//    @Override
//    public BookingDto addBooking(BookingDto bookingDto) throws FlightNotFoundException {
//        // First, check if the flight exists
//        Long flightNumber = bookingDto.getFlightNumber();
//        Flight flight = getFlightDetails(flightNumber);
//        
//        if (flight == null) {
//            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
//        }
//
//        // Proceed with creating the booking if the flight exists
//        Booking booking = convertToEntity(bookingDto);
//        Booking savedBooking = bookingRepository.save(booking);
//        return convertToDto(savedBooking);
//    }
//
//    @Override
//    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
//        Optional<Booking> booking = bookingRepository.findById(bookingId);
//        if (booking.isPresent()) {
//            bookingRepository.delete(booking.get());
//        } else {
//            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
//        }
//    }
//
//    @Override
//    public BookingDto viewBooking(Long bookingId) throws BookingNotFoundException {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
//        return convertToDto(booking);
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(LocalDate bookingDate) {
//        List<Booking> bookings = bookingRepository.findByBookingDate(bookingDate);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(Long flightNumber) {
//        List<Booking> bookings = bookingRepository.findByFlightNumber(flightNumber);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingHistory(Long userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public BookingDto updateBooking(BookingDto bookingDto) {
//        Booking booking = convertToEntity(bookingDto);
//        Booking updatedBooking = bookingRepository.save(booking);
//        return convertToDto(updatedBooking);
//    }
//    
//    @Override
//    public List<BookingDto> getAllBookings() {
//        List<Booking> bookings = bookingRepository.findAll();
//        if (bookings.isEmpty()) {
//            throw new BookingNotFoundException("No bookings found in the database");
//        }
//        return bookings.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private Booking convertToEntity(BookingDto bookingDto) {
//        Booking booking = new Booking();
//        booking.setBookingId(bookingDto.getBookingId());
//        booking.setUserId(bookingDto.getUserId());
//        booking.setBookingDate(bookingDto.getBookingDate());
//        booking.setTicketCost(bookingDto.getTicketCost());
//        booking.setFlightNumber(bookingDto.getFlightNumber());
//        booking.setNoOfPassengers(bookingDto.getNoOfPassengers());
//        return booking;
//    }
//
//    private BookingDto convertToDto(Booking booking) {
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setBookingId(booking.getBookingId());
//        bookingDto.setUserId(booking.getUserId());
//        bookingDto.setBookingDate(booking.getBookingDate());
//        bookingDto.setTicketCost(booking.getTicketCost());
//        bookingDto.setFlightNumber(booking.getFlightNumber());
//        bookingDto.setNoOfPassengers(booking.getNoOfPassengers());
//        return bookingDto;
//    }
//
//    public Flight getFlightDetails(Long flightNumber) throws FlightNotFoundException {
//        try {
//            return flightServiceClient.getFlightByNumber(flightNumber);
//        } catch (FeignException.NotFound e) {
//            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
//        }
//        
//    }
//    
//    public User getUserDetails(Long userId) throws UserNotFoundException {
//        try {
//            return userServiceClient.getUserById(userId);
//        } catch (FeignException.NotFound e) {
//            throw new UserNotFoundException("User with ID " + userId + " not found");
//        }
//       
//}
//
//package com.capgemini.Booking.Service;
//
//import java.time.LocalDate;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import com.capgemini.Booking.Client.FlightServiceClient;
//
//import com.capgemini.Booking.Client.UserServiceClient;
//import com.capgemini.Booking.Dto.BookingDto;
//import com.capgemini.Booking.Dto.Flight;
//import com.capgemini.Booking.Dto.User;
//import com.capgemini.Booking.Exception.BookingNotFoundException;
//import com.capgemini.Booking.Exception.FlightNotFoundException;
//import com.capgemini.Booking.Exception.UserNotFoundException;
//import com.capgemini.Booking.entity.Booking;
//import com.capgemini.Booking.repository.IFlightBookingRepository;
//import feign.FeignException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BookingServiceImpl implements BookingService {
//
//    @Autowired
//    private IFlightBookingRepository bookingRepository;
//    
//    @Autowired
//    private FlightServiceClient flightServiceClient;
//    
//    @Autowired
//    private UserServiceClient userServiceClient;
////    @Autowired
////    private PassengerServiceClient passengerServiceClient; // Feign client for passenger service
//
//    @Override
//    public BookingDto addBooking(BookingDto bookingDto) throws FlightNotFoundException {
//        // First, check if the flight exists
//        Long flightNumber = bookingDto.getFlightNumber();
//        Flight flight = getFlightDetails(flightNumber);
//        
//        if (flight == null) {
//            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
//        }
//
//        // Proceed with creating the booking if the flight exists
//        Booking booking = convertToEntity(bookingDto);
//        Booking savedBooking = bookingRepository.save(booking);
//        return convertToDto(savedBooking);
//    }
//
//    @Override
//    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
//        Optional<Booking> booking = bookingRepository.findById(bookingId);
//        if (booking.isPresent()) {
//            bookingRepository.delete(booking.get());
//        } else {
//            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
//        }
//    }
//
//    @Override
//    public BookingDto viewBooking(Long bookingId) throws BookingNotFoundException {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
//        return convertToDto(booking);
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(LocalDate bookingDate) {
//        List<Booking> bookings = bookingRepository.findByBookingDate(bookingDate);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingList(Long flightNumber) {
//        List<Booking> bookings = bookingRepository.findByFlightNumber(flightNumber);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> viewBookingHistory(Long userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public BookingDto updateBooking(BookingDto bookingDto) {
//        Booking booking = convertToEntity(bookingDto);
//        Booking updatedBooking = bookingRepository.save(booking);
//        return convertToDto(updatedBooking);
//    }
//    
//    @Override
//    public List<BookingDto> getAllBookings() {
//        List<Booking> bookings = bookingRepository.findAll();
//        if (bookings.isEmpty()) {
//            throw new BookingNotFoundException("No bookings found in the database");
//        }
//        return bookings.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private Booking convertToEntity(BookingDto bookingDto) {
//        Booking booking = new Booking();
//        booking.setBookingId(bookingDto.getBookingId());
//        booking.setUserId(bookingDto.getUserId());
//        booking.setBookingDate(bookingDto.getBookingDate());
//        booking.setTicketCost(bookingDto.getTicketCost());
//        booking.setFlightNumber(bookingDto.getFlightNumber());
//        booking.setNoOfPassengers(bookingDto.getNoOfPassengers());
//        return booking;
//    }
//
//    private BookingDto convertToDto(Booking booking) {
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setBookingId(booking.getBookingId());
//        bookingDto.setUserId(booking.getUserId());
//        bookingDto.setBookingDate(booking.getBookingDate());
//        bookingDto.setTicketCost(booking.getTicketCost());
//        bookingDto.setFlightNumber(booking.getFlightNumber());
//        bookingDto.setNoOfPassengers(booking.getNoOfPassengers());
//        return bookingDto;
//    }
//
//    public Flight getFlightDetails(Long flightNumber) throws FlightNotFoundException {
//        try {
//            return flightServiceClient.getFlightByNumber(flightNumber);
//        } catch (FeignException.NotFound e) {
//            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
//        }
//    }
//    
//    public User getUserDetails(Long userId) throws UserNotFoundException {
//        try {
//            return userServiceClient.getUserById(userId);
//        } catch (FeignException.NotFound e) {
//            throw new UserNotFoundException("User with ID " + userId + " not found");
//        }
//    }
//}

package com.capgemini.Booking.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.capgemini.Booking.Client.FlightServiceClient;
import com.capgemini.Booking.Client.UserServiceClient;
import com.capgemini.Booking.Dto.BookingDto;
import com.capgemini.Booking.Dto.Flight;
import com.capgemini.Booking.Dto.User;
import com.capgemini.Booking.Exception.BookingNotFoundException;
import com.capgemini.Booking.Exception.FlightNotFoundException;
import com.capgemini.Booking.Exception.UserNotFoundException;
import com.capgemini.Booking.entity.Booking;
import com.capgemini.Booking.repository.IFlightBookingRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private IFlightBookingRepository bookingRepository;
    
    @Autowired
    private FlightServiceClient flightServiceClient;
    
    @Autowired
    private UserServiceClient userServiceClient; // Feign client to validate user
    
//    @Autowired
//    private PassengerServiceClient passengerServiceClient; // Feign client for passenger service

//    @Override
//    public BookingDto addBooking(BookingDto bookingDto) throws FlightNotFoundException, UserNotFoundException {
//        // First, check if the flight exists
//        Long flightNumber = bookingDto.getFlightNumber();
//        Flight flight = getFlightDetails(flightNumber);
//        
//        if (flight == null) {
//            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
//        }
//
//        // Validate if the user exists
//        Long userId = bookingDto.getUserId();
//        User user = UserServiceClient.getUserById(userId);
//        
//        if (user == null) {
//            throw new UserNotFoundException("User with ID " + userId + " not found");
//        }
//
//        // Proceed with creating the booking if the flight and user exist
//        Booking booking = convertToEntity(bookingDto);
//        Booking savedBooking = bookingRepository.save(booking);
//        return convertToDto(savedBooking);
//    }
    @Override
    public BookingDto addBooking(BookingDto bookingDto) throws FlightNotFoundException, UserNotFoundException {
        // First, check if the flight exists
        Long flightNumber = bookingDto.getFlightNumber();
        Flight flight = getFlightDetails(flightNumber);
        
        if (flight == null) {
            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
        }

        // Validate if the user exists using the userServiceClient
        Long userId = bookingDto.getUserId();
        try {
            User user = userServiceClient.getUserById(userId); // Direct call to Feign client
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userId + " not found");
            }
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        // Proceed with creating the booking if the flight and user exist
        Booking booking = convertToEntity(bookingDto);
        Booking savedBooking = bookingRepository.save(booking);
        return convertToDto(savedBooking);
    }

    @Override
    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
        } else {
            throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
        }
    }

    @Override
    public BookingDto viewBooking(Long bookingId) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
        return convertToDto(booking);
    }

    @Override
    public List<BookingDto> viewBookingList(LocalDate bookingDate) {
        List<Booking> bookings = bookingRepository.findByBookingDate(bookingDate);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> viewBookingList(Long flightNumber) {
        List<Booking> bookings = bookingRepository.findByFlightNumber(flightNumber);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> viewBookingHistory(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        Booking booking = convertToEntity(bookingDto);
        Booking updatedBooking = bookingRepository.save(booking);
        return convertToDto(updatedBooking);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings found in the database");
        }
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Booking convertToEntity(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setBookingId(bookingDto.getBookingId());
        booking.setUserId(bookingDto.getUserId());
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setTicketCost(bookingDto.getTicketCost());
        booking.setFlightNumber(bookingDto.getFlightNumber());
        booking.setNoOfPassengers(bookingDto.getNoOfPassengers());
        return booking;
    }

    private BookingDto convertToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getBookingId());
        bookingDto.setUserId(booking.getUserId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setTicketCost(booking.getTicketCost());
        bookingDto.setFlightNumber(booking.getFlightNumber());
        bookingDto.setNoOfPassengers(booking.getNoOfPassengers());
        return bookingDto;
    }

    public Flight getFlightDetails(Long flightNumber) throws FlightNotFoundException {
        try {
            return flightServiceClient.getFlightByNumber(flightNumber);
        } catch (FeignException.NotFound e) {
            throw new FlightNotFoundException("Flight with flight number " + flightNumber + " not found");
        }
    }

//    public User getUserDetails(Long userId) throws UserNotFoundException {
//        try {
//            return userServiceClient.getUserById(userId);
//        } catch (FeignException.NotFound e) {
//            throw new UserNotFoundException("User with ID " + userId + " not found");
//        }
//    }
}
