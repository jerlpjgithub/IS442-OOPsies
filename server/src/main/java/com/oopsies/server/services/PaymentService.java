package com.oopsies.server.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.PaymentDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Payment;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.PaymentRepository;

/**
 * PaymentService class is a service class that handles all the payment related operations.
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public PaymentService() { }

    /**
     * getPaymentById is a method that returns a payment object by its paymentId.
     * @param paymentId is the id of the payment object.
     * @return a payment object.
    */
    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findPaymentByPaymentId(paymentId);
    }

    /**
     * getPaymentByBookingId is a method that returns a payment object by its bookingId.
     * @param bookingId is the id of the booking object.
     * @return a PaymentDTO object.
    */
    public PaymentDTO getPaymentByBookingId(long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId);
        return convertToDTO(payment);
    }
    

    /**
     * processPayment is a method that processes a payment for a booking.
     * @param user is the user that is making the payment.
     * @param booking is the booking object.
     * @param event is the event object.
     * @param numTickets is the number of tickets that the user is buying.
     * @return the paymentId of the payment object.
    */
    public long processPayment(User user, Booking booking,EventDTO event, int numTickets){
        double totalPrice = getTotalPrice(event, numTickets);
        user.decrementAccountBalance(totalPrice);
        userDetailsService.saveUser(user);

        return createNewPayment(booking, totalPrice);
    }

    /**
     * getTotalPrice is a method that calculates the total price of the tickets.
     * @param event is the event object.
     * @param numTickets is the number of tickets that the user is buying.
     * @return the total price of the tickets.
    */
    private double getTotalPrice(EventDTO event, int numTickets) {
        double ticketPrice = event.getTicketPrice();
        return ticketPrice * numTickets;
    }

    /**
     * createNewPayment is a method that creates a new payment object.
     * @param booking is the booking object.
     * @param paymentAmount is the amount of the payment.
     * @return the paymentId of the payment object.
    */
   private long createNewPayment(Booking booking, double paymentAmount) {
       Payment newPayment = new Payment(
            booking,
            paymentAmount,
            new Date()
       );
       Payment payment = paymentRepository.save(newPayment);
       return payment.getPaymentId();
   }

   /**
     * convertToDTO is a helper method that converts a payment object to a PaymentDTO object.
     * @param payment is the payment object.
     * @return a PaymentDTO object.
    */
    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentID(payment.getPaymentId());
        return dto;
    }
}

