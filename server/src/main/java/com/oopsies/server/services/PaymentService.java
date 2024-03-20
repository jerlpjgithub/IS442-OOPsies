package com.oopsies.server.services;

import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.PaymentDTO;
import com.oopsies.server.entity.*;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

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

    public long processPayment(User user, Booking booking,EventDTO event, int numTickets) throws UserInsufficientFundsException {
        double totalPrice = getTotalPrice(event, numTickets);
        if (!hasUserValidBalance(user, totalPrice)) {
            throw new UserInsufficientFundsException();
        }
        user.decrementAccountBalance(totalPrice);
        userDetailsService.saveUser(user);

        return createNewPayment(booking, totalPrice);
    }

    private double getTotalPrice(EventDTO event, int numTickets) {
        double ticketPrice = event.getTicketPrice();
        return ticketPrice * numTickets;
    }

    private boolean hasUserValidBalance(User user, double price) {
        return user.getAccountBalance() >= price;
    }

   private long createNewPayment(Booking booking, double paymentAmount) {
       Payment newPayment = new Payment(
               booking,
               paymentAmount,
               new Date()
       );
       Payment payment = paymentRepository.save(newPayment);
       return payment.getPaymentId();
   }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentID(payment.getPaymentId());
        return dto;
    }
}
