package com.oopsies.server.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class BookingController {
    
    @Autowired
    private BookingService bookingService;


    @PostMapping("/bookings")
    public Booking CreateBookingInstance(){
        Booking booking = new BookingService();

    }

    @PostMapping("/Payment")
    public Payment MakePayment(){
        Payment payment = new Payment();
    }

    @PostMapping("/send-email")
    public void SendEmail(){
        Emailsender email = new Emailsender();
    }

}