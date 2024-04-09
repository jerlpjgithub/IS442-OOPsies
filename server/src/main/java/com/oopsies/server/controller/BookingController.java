package com.oopsies.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.EmailStructure;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.payload.request.BookingRequest;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.TicketRepository;
import com.oopsies.server.services.BookingService;
import com.oopsies.server.services.TicketService;
import com.oopsies.server.services.UserServiceImpl;


@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired 
    private EmailController emailController;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    public BookingController(BookingService bookingService){
      this.bookingService = bookingService;
    }

    @PostMapping(path = "/create/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public ResponseEntity<?> createBooking(@PathVariable(value="user_id") long user_id, @RequestBody BookingRequest bookingRequest){
        try {
            long eventId = bookingRequest.getEventId();
            int numTickets = bookingRequest.getNumTickets();
            BookingDTO bookingDTO = bookingService.createBooking(user_id, eventId, numTickets);

            //call emailController method 
            //get user
            User user = userService.getUserById(user_id);

            //get booking parameters first
            String name = user.getFirstName() + user.getLastName();
            // String email = user.getEmail();
            String email = "laiu.asher@gmail.com"; //this will need to change when we have proper user emails
            long bookingID = bookingDTO.getBookingID();
            Date bookingDate = bookingDTO.getBookingDate();

            //get event parameters
            EventDTO event = bookingDTO.getEvent();
            String eventName = event.getEventName();
            Date eventDate = event.getDateTime();
            Date refundDate = null;
            String venue = event.getVenue();
            String type = "Booking Confirmation";

            //get ticket parameters
            List<Ticket> tickets = ticketRepository.findTicketsByBooking(bookingRepository.findBookingById(bookingID));
            List<Long> ticketIDs = new ArrayList<>();
            for (Ticket ticket : tickets) {
                ticketIDs.add(ticket.getId());
            }
            double ticketPrice = event.getTicketPrice();
            double totalPrice = ticketPrice * tickets.size();

            //send email
            EmailStructure emailStructure = new EmailStructure(name, email, bookingID, bookingDate, eventName, 
                                                                eventDate, refundDate, venue, ticketIDs, 
                                                                totalPrice, 0, type);
            emailController.sendEmail(email, emailStructure);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(
                    201, "Booking was created successfully!", bookingDTO
            ));
        }
        catch (IllegalArgumentException | UserInsufficientFundsException exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(
                    400, exc.getMessage(), null
            ));
        }
        catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }

    @GetMapping(path = "/get/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable("user_id") long user_id){
        List<BookingDTO> _bookings = bookingService.findBookingsByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
                200, "successful", _bookings
        ));
    }

    @PostMapping(path = "/refund/{booking_id}")
    public ResponseEntity<?> initiateRefundByBookingId(@PathVariable("booking_id") long booking_id){
        try{
            bookingService.processBookingRefund(booking_id);
            
            //send emails for refund confirmation
            Booking booking = bookingRepository.findBookingById(booking_id);
            User user = booking.getUser();

            //get user details
            String name = user.getFirstName() + user.getLastName();
            // String email = user.getEmail();
            String email = "laiu.asher@gmail.com"; //this will need to change when we have proper user emails
            long bookingID = booking.getBookingID();
            Date bookingDate = booking.getBookingDate();

            //get event parameters
            Event event = booking.getEvent();
            String eventName = event.getEventName();
            Date eventDate = event.getDateTime();
            Date refundDate = booking.getCancelDate();
            String venue = event.getVenue();
            String type = "Refund Confirmation";

            //get ticket parameters
            List<Ticket> tickets = ticketRepository.findTicketsByBooking(bookingRepository.findBookingById(bookingID));
            List<Long> ticketIDs = new ArrayList<>();
            for (Ticket ticket : tickets) {
                ticketIDs.add(ticket.getId());
            }
            double ticketPrice = event.getTicketPrice();
            double totalPrice = ticketPrice * tickets.size();
            double penaltyFee = event.getCancellationFee();
            double refundedAmount = totalPrice - penaltyFee;

            //send email
            EmailStructure emailStructure = new EmailStructure(name, email, bookingID, bookingDate, eventName, 
                                                                eventDate, refundDate, venue, ticketIDs, refundedAmount,
                                                                penaltyFee, type);
            emailController.sendEmail(email, emailStructure);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
            200, "refund processed successfully", null
        ));
        }
        catch(Exception exc){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(
                    500, exc.getMessage(), null
            ));
        }
    }

    // @GetMapping("/get/{event_id}")
    // //get all bookings by event id. from this, we can derive ticket sales, revenue and customer attendance
    // public ResponseEntity<?> getBookingsByEventID(@PathVariable("event_id") long eventId) {
    //    List<BookingDTO> _bookings = bookingService.findBookingsByEventID(eventId);
    //     return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<>(
    //             200, "successful", _bookings
    //     ));
    // }

    
}