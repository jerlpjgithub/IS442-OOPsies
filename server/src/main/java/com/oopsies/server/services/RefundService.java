package com.oopsies.server.services;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Payment;
import com.oopsies.server.entity.Refund;
import com.oopsies.server.entity.User;
import com.oopsies.server.entity.Event;

import com.oopsies.server.repository.RefundRepository;
import com.oopsies.server.repository.TicketRepository;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.repository.PaymentRepository;
import com.oopsies.server.repository.UserRepository;

import java.util.Optional;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class RefundService {

  private RefundRepository refundRepository;

  private BookingRepository bookingRepository;

  private UserRepository userRepository;

  private PaymentRepository paymentRepository;

  private TicketRepository ticketRepository;

  private EventRepository eventRepository;

  public RefundService(RefundRepository refundRepository, BookingRepository bookingRepository, UserRepository userRepository, PaymentRepository paymentRepository, TicketRepository ticketRepository, EventRepository eventRepository) { 
    this.refundRepository = refundRepository;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.paymentRepository = paymentRepository;
    this.ticketRepository = ticketRepository;
    this.eventRepository = eventRepository;
  }
  // public Refund createRefund(Refund refund){
  //   return refundRepository.save(refund);
  // }

  public void processRefund(Long booking_id) throws Exception{

    // Check if refund has already been requested
    Refund refund = refundRepository.findRefundByBookingId(booking_id);
    if(refund != null){
      throw new Exception("refund for this booking has already been processed");
    }


    Booking booking = bookingRepository.findBookingById(booking_id);
    Optional<Event> event = eventRepository.findById(booking.getEventID().getId());

    boolean within48hrs = within48HoursOfEventStart(event.get());
    if(within48hrs){
      throw new Exception("cannot refund within 48 hours of event start");
    }

    Refund processedRefund = new Refund(booking_id, booking, new Date());
    long user_id = booking.getUser().getId();

    // To change once relation is correct 

    Payment payment = paymentRepository.findByBookingId(booking_id);
    double refundedAmount =  payment.getAmount();
    
    // Retrieves current user that booked, and refund amount back to them. 
    Optional<User> user = userRepository.findById(user_id);
    double accountBalance = user.get().getAccountBalance();
    double updatedAccountBalance = accountBalance + refundedAmount;
    user.get().setAccountBalance(updatedAccountBalance);
  
    userRepository.save(user.get());

    // Retrieve tickets booked and reallocates them to event ticket capacity
    int numOfTickets = ticketRepository.findTicketsByBooking(booking).size();
    int currentCapacity =  event.get().getCapacity();
    event.get().setCapacity(numOfTickets + currentCapacity);
    eventRepository.save(event.get());
    
    refundRepository.save(processedRefund);
  }

  public static boolean within48HoursOfEventStart(Event event){
    Date currentTime = new Date();
    Date eventTime = event.getDateTime();

    long timeDifference = Math.abs(currentTime.getTime() - eventTime.getTime());
    long hoursDifference = timeDifference / (1000 * 60 * 60);


    if (hoursDifference < 48) {
          return true;
        } 
    return false;
  }


}
