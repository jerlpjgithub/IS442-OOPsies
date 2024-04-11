package com.oopsies.server.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.PaymentDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Payment;
import com.oopsies.server.entity.User;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.repository.PaymentRepository;

/**
 * Service for handling Payment entities
 * This service contains methods for creating payments
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public PaymentService() { }

    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findPaymentByPaymentId(paymentId);
    }

    public PaymentDTO getPaymentByBookingId(long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId);
        return convertToDTO(payment);
    }

    public void processPayment(User user, Booking booking,EventDTO event, int numTickets){
        double totalPrice = getTotalPrice(event, numTickets);
        user.decrementAccountBalance(totalPrice);
        userDetailsService.saveUser(user);

        createNewPayment(booking, totalPrice);
    }

    private double getTotalPrice(EventDTO event, int numTickets) {
        double ticketPrice = event.getTicketPrice();
        return ticketPrice * numTickets;
    }

   private void createNewPayment(Booking booking, double paymentAmount) {
       Payment newPayment = new Payment(
               booking,
               paymentAmount,
               new Date()
       );
       Payment payment = paymentRepository.save(newPayment);
   }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentID(payment.getPaymentId());
        return dto;
    }
}

