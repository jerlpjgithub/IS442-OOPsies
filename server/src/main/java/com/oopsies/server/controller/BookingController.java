package com.oopsies.server.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Payment;
import com.oopsies.server.services.BookingService;
import com.oopsies.server.services.PaymentService;
import com.oopsies.server.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/bookings")
    public Booking CreateBookingInstance(){
        Booking booking = new Booking();
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    // assuming that user ID is passed via request headers
    public void createPayment(@RequestHeader Map<String, String> headers) {
        // TODO get user object
//        String userId = headers.get("userId");
        // TODO get booking object
        // TODO process payment
//        paymentService.processPayment();
    }

    @PostMapping("/send-email")
    public void SendEmail(){
        Emailsender email = new Emailsender();
    }

}