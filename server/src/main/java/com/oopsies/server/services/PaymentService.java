// package com.oopsies.server.services;

// import com.oopsies.server.entity.*;
// import com.oopsies.server.exception.UserInsufficientFundsException;
// import com.oopsies.server.repository.PaymentRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class PaymentService {

//     @Autowired
//     private PaymentRepository paymentRepository;

//     @Autowired
//     private TicketService ticketService;

//     @Autowired
//     private EventService eventService;

//     public PaymentService() { }

//     public Payment getPaymentById(int paymentId) {
//         return paymentRepository.findPaymentByPaymentId(paymentId);
//     }

//     public List<Payment> getPaymentsByUserId(User user) {
//         return paymentRepository.findPaymentsByUserId(user);
//     }
    
//     // ------- METHODS DONT WORK YET SINCE bookingDetails doesnt have .getEventID() =-------

//   //  public void processPayment(User user, Booking bookingDetails) throws UserInsufficientFundsException {
//   //       double totalPrice = getTotalPrice(bookingDetails);
//   //       if (!hasUserValidBalance(user, totalPrice)) {
//   //           throw new UserInsufficientFundsException();
//   //       }
//   //       user.decrementAccountBalance(totalPrice);
//   //   }

//   //   public void processRefund(User user, Booking bookingDetails) {
//   //       double cancellationFee = getCancellationFee(bookingDetails);
//   //       double totalPrice = getTotalRefundPrice(bookingDetails);
//   //       user.incrementAccountBalance(totalPrice);

//   //   }

//     // private double getCancellationFee(Booking bookingDetails) {
//     //     Event event = eventService.getEventById(bookingDetails.getEventID());
//     //     return event.getCancellationFee();
//     // }

//     // private double getTotalPrice(Booking bookingDetails) {
//     //     int numTickets = getNumberOfTickets(bookingDetails);
//     //     Event event = eventService.getEventById(bookingDetails.getEventID());
//     //     double ticketPrice = event.getTicketPrice();

//     //     return ticketPrice * numTickets;
//     // }

//     // private double getTotalRefundPrice(Booking bookingDetails) {
//     //     int numTickets = getNumberOfTickets(bookingDetails);
//     //     Event event = eventService.getEventById(bookingDetails.getEventID());
//     //     double ticketPrice = event.getTicketPrice();
//     //     double cancellationFee = event.getCancellationFee();

//     //     return (ticketPrice - cancellationFee) * numTickets;
//     // }

//     private int getNumberOfTickets(Booking bookingDetails) {
//         List<Ticket> tickets = ticketService.getAllTicketsForBooking(bookingDetails.getBookingID());
//         return tickets.size();
//     }

//     private boolean hasUserValidBalance(User user, double price) {
//         return user.getAccountBalance() >= price;
//     }
// }

