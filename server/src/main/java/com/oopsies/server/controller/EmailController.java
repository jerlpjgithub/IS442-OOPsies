package com.oopsies.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.entity.EmailStructure;
import com.oopsies.server.services.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping(path = "/send/{email}")
    // @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public ResponseEntity<?> sendEmail(@PathVariable String email, @RequestBody EmailStructure emailStructure) {

        try {
            emailService.sendEmail(email, emailStructure);
            return ResponseEntity.status(200).body("Email Sent Successfully");

        } catch (MessagingException e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error");
        }

            //         //call EmailController method
            // //get booking parameters first
            // String name = "Asher Laiu";
            // String email = "laiu.asher@gmail.com";
            // long bookingID = bookingDTO.getBookingID();
            // Date bookingDate = bookingDTO.getBookingDate();
            // //get event parameters
            // EventDTO event = bookingDTO.getEvent();
            // String eventName = event.getEventName();
            // Date eventDate = event.getDateTime();
            // Date refundDate = null;
            // String venue = event.getVenue();
            // String type = "Booking Confirmation";
            // // long eventID = bookingRequest.getEventId();

            // EmailStructure emailStructure = new EmailStructure(name, email, bookingID, bookingDate, eventName, eventDate, refundDate, venue, type);
            // emailController.sendEmail("laiu.asher@gmail.com", emailStructure);
    }


    //test mapping 
    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public String getEmail() {
        return "Accessed";
    }

}
