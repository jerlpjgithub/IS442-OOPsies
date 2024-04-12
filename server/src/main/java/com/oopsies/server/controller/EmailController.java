package com.oopsies.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.entity.EmailStructure;
import com.oopsies.server.services.EmailService;

import jakarta.mail.MessagingException;

/**
 * The EmailController class handles HTTP requests related to emails.
 * It uses the EmailService to perform operations.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * Constructs a new EmailController with the specified EmailService.
     *
     * @param emailService The service to use for email operations.
     */
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Sends an email.
     *
     * @param email The email address to send the email to.
     * @param emailStructure The structure of the email to send.
     * @return A ResponseEntity with a 200 status and a success message if the email was sent successfully, or a 500 status and an error message if an exception occurred.
     */
    @PostMapping(path = "/send/{email}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER') or #user_id == authentication.principal.id")
    public ResponseEntity<?> sendEmail(@PathVariable String email, @RequestBody EmailStructure emailStructure) {

        try {
            emailService.sendEmail(email, emailStructure);
            return ResponseEntity.status(200).body("Email Sent Successfully");

        } catch (MessagingException e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error");
        }
    }

}
