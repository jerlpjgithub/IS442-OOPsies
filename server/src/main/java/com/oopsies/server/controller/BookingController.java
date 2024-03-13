package com.oopsies.server.controller;

import java.util.*;
import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.payload.response.MessageResponse;
// import com.oopsies.server.entity.Payment;
import com.oopsies.server.services.BookingService;
// import com.oopsies.server.services.PaymentService;
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
    public ResponseEntity<?> createBooking(@PathVariable(value="user_id") long user_id,
      @RequestBody Booking booking, @RequestParam int numGuests
      ){
        try {
          Booking _booking = bookingService.createBooking(user_id, booking, numGuests);
          return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Successfully created booking"));
        }
        catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("404 NOT FOUND"));
        }
    }

    @GetMapping(path = "/get-booking/{user_id}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable("user_id") long user_id){
      // try{
        List<BookingDTO> _bookings = bookingService.findBookingsByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(_bookings);
      // }
      // catch(Exception exc){
      //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("404 NOT FOUND"));
      // }
      
    }

//     @Autowired
//     private PaymentService paymentService;

//     @PostMapping("/bookings")
//     public Booking CreateBookingInstance(){
//         Booking booking = new Booking();
//     }

//     @RequestMapping(value = "/bookings/payment", method = RequestMethod.POST)
//     // assuming that user ID is passed via request headers
//     public ResponseEntity<?> createPayment(@RequestParam("userId") String userId) {
//         // TODO get user object
//         // TODO get booking object
//         // TODO process payment
// //        paymentService.processPayment();
//     }

//     @PostMapping("/send-email")
//     public void SendEmail(){
//         Emailsender email = new Emailsender();
//     }

}