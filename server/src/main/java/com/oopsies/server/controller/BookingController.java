package com.oopsies.server.controller;

import java.util.*;
import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.*;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.payload.request.BookingRequest;
import com.oopsies.server.payload.request.OnsiteBookingRequest;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * BookingController class is a RestController that handles all the HTTP
 * requests related to bookings.
 */
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Constructs a new BookingController with the specified BookingService.
     *
     * @param bookingService the BookingService to use
     */
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * createBooking method is a POST request that creates a booking for a user.
     * 
     * @param user_id        the id of the user
     * @param bookingRequest the booking request
     * @return ResponseEntity&lt;?&gt; returns a message response with the status code and
     *         message
     */
    @PostMapping(path = "/create/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER') and #user_id == authentication.principal.id")
    public ResponseEntity<?> createBooking(@PathVariable(value = "user_id") long user_id,
            @RequestBody BookingRequest bookingRequest) {
        try {
            long eventId = bookingRequest.getEventId();
            int numTickets = bookingRequest.getNumTickets();
            BookingDTO bookingDTO = bookingService.createBooking(user_id, eventId, numTickets);

            // get user
            User user = userService.getUserById(user_id);

            // call emailController method
            emailService.createEmail(user, bookingDTO, "Booking Confirmation");

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
                    201, "Booking was created successfully!", bookingDTO));
        } catch (IllegalArgumentException | UserInsufficientFundsException exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
                    400, exc.getMessage(), null));
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null));
        }
    }

    /**
     * createOnsiteBooking method is a POST request that creates a booking for a
     * user on-site.
     * 
     * @param bookingRequest the booking request
     * @return ResponseEntity&lt;?&gt; returns a message response with the status code and
     *         message
     */
    @PostMapping(path = "/create/on-site")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER')")
    public ResponseEntity<?> createOnsiteBooking(@RequestBody OnsiteBookingRequest bookingRequest) {
        try {
            long eventId = bookingRequest.getEventId();
            int numTickets = bookingRequest.getNumTickets();
            String userEmail = bookingRequest.getUserEmail();

            Optional<EventDTO> someEvent = eventService.getEventById(eventId);
            if (someEvent.isEmpty()) {
                throw new IllegalArgumentException("Event not found");
            }
            EventDTO event = someEvent.get();
            double paymentAmount = event.getTicketPrice() * numTickets;

            Optional<User> someUser = userRepository.findByEmail(userEmail);
            User user;
            if (someUser.isEmpty()) {
                user = new User(
                        userEmail,
                        encoder.encode("newCustomer"),
                        "New",
                        "User",
                        paymentAmount);

                // Default Role User is set
                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(
                                "Error: User role is not found."));
                Set<Role> roles = new HashSet<>();
                roles.add(userRole);

                user.setRoles(roles);
                System.out.println(user);
                userRepository.save(user);
            } else {
                user = someUser.get();
                user.incrementAccountBalance(paymentAmount);
            }

            BookingDTO bookingDTO = bookingService.createBooking(user.getId(), eventId, numTickets);

            // call emailController method
            emailService.createEmail(user, bookingDTO, "Booking Confirmation");

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
                    201, "Booking was created successfully!", bookingDTO));
        } catch (IllegalArgumentException | UserInsufficientFundsException exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
                    400, exc.getMessage(), null));
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null));
        }
    }

    /**
     * getBookingsByUserId method is a GET request that retrieves all bookings for a
     * user.
     * 
     * @param user_id the id of the user
     * @return ResponseEntity&lt;?&gt; returns a message response with the status code and
     *         message
     */
    @GetMapping(path = "/get/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable("user_id") long user_id) {
        List<BookingDTO> _bookings = bookingService.findBookingsByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _bookings));
    }

    /**
     * initiateRefundByBookingId method is a POST request that initiates a refund
     * for a booking.
     * 
     * @param booking_id the id of the booking
     * @return ResponseEntity&lt;?&gt; returns a message response with the status code and
     *         message
     */
    @PostMapping(path = "/refund/{booking_id}")
    public ResponseEntity<?> initiateRefundByBookingId(@PathVariable("booking_id") long booking_id) {
        try {
            bookingService.processBookingRefund(booking_id);

            // send emails for refund confirmation
            Booking booking = bookingRepository.findBookingById(booking_id);
            User user = booking.getUser();

            emailService.createEmail(user, bookingService.convertToDTO(booking), "Refund Confirmation");

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                    200, "refund processed successfully", null));
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null));
        }
    }
}