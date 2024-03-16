package com.oopsies.server.controller;

import java.sql.SQLException;
import java.util.*;
import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.payload.request.BookingRequest;
import com.oopsies.server.payload.response.MessageResponse;
// import com.oopsies.server.entity.Payment;
import com.oopsies.server.services.BookingService;
// import com.oopsies.server.services.PaymentService;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookingController(BookingService bookingService){
      this.bookingService = bookingService;
    }

    @PostMapping(path = "/{user_id}/create-booking")
    public ResponseEntity<?> createBooking(@PathVariable(value="user_id") long user_id, @RequestBody BookingRequest bookingRequest){
        try {
            long eventId = bookingRequest.getEventId();
            int numTickets = bookingRequest.getNumTickets();
            BookingDTO bookingDTO = bookingService.createBooking(user_id, eventId, numTickets);
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

    @GetMapping(path = "/get-booking/{user_id}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable("user_id") long user_id){
      // try{
        List<BookingDTO> _bookings = bookingService.findBookingsByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<List<BookingDTO>>(
                200, "successful", _bookings
        ));
      // }
      // catch(Exception exc){
      //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("404 NOT FOUND"));
      // }
      
    }
}