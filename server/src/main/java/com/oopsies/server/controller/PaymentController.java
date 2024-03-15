package com.oopsies.server.controller;

import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.PaymentDTO;
import com.oopsies.server.entity.Payment;
import com.oopsies.server.entity.User;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.EventService;
import com.oopsies.server.services.PaymentService;
import com.oopsies.server.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private EventService eventService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/{userId}/create-payment")
    public ResponseEntity<?> createPayment(@PathVariable(value="userId") long userId, @RequestParam int numTickets
    ){
        try {
            Optional<User> user = userDetailsService.getUserById(userId);
            if (user.isEmpty()) {
                throw new IllegalArgumentException("User " + userId + " does not exist!");
            }
            paymentService.processPayment(user.get(),
                    eventService.getEventById(1).get(),
                    numTickets);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Successfully created payment"));
        } catch (UserInsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/get-payment/{user_id}")
    public ResponseEntity<?> getPaymentsByUserId(@PathVariable(value="user_id") long userId){
        List<PaymentDTO> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }
}
