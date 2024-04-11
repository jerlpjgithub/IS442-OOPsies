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

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling Refund entities
 * This service contains methods for processing refunds
 */
@Service
public class RefundService {

  @Autowired
  private RefundRepository refundRepository;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  public RefundService(RefundRepository refundRepository, BookingRepository bookingRepository,
      UserRepository userRepository, PaymentRepository paymentRepository, TicketRepository ticketRepository,
      EventRepository eventRepository) {
    this.refundRepository = refundRepository;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.paymentRepository = paymentRepository;
    this.ticketRepository = ticketRepository;
    this.eventRepository = eventRepository;
  }

  public void cancelEvent(long event_id) {
    // get event object based on event_id, check if user requesting (logged in) is
    // indeed event manager
    Optional<Event> someEvent = eventRepository.findById(event_id);
    if (someEvent.isEmpty()) {
      throw new IllegalArgumentException("Invalid event requested");
    }
    Event event = someEvent.get();
    if (!userDetailsService.isAuthorisedUser(event.getManagerID())) {
      throw new IllegalArgumentException("Unauthorised content!");
    }

    List<Booking> listOfBookings = bookingRepository.findByEventId(event_id);

    for (Booking booking : listOfBookings) {
      if (booking.getCancelDate() == null) {
        booking.setCancelDate(new Date());
        bookingRepository.save(booking);

        Refund processedRefund = new Refund(booking, new Date());

        // Retrieves each current user that booked, and refund amount back to them.
        User user = booking.getUser();

        Payment payment = paymentRepository.findByBookingId(booking.getBookingID());
        double refundedAmount = payment.getAmount();

        double accountBalance = user.getAccountBalance();
        double updatedAccountBalance = accountBalance + refundedAmount;

        user.setAccountBalance(updatedAccountBalance);
        userRepository.save(user);

        refundRepository.save(processedRefund);
      }
    }

    event.setEventCancelled(new Date());
    eventRepository.save(event);
  }

  public void processRefund(Long booking_id) throws Exception {
    // TODO refactor for single purpose, booking checks should be done in booking
    // service
    // Check if refund has already been requested (cancelled_date for Booking has a
    // value)
    Booking booking = bookingRepository.findBookingById(booking_id);
    if (!userDetailsService.isAuthorisedUser(booking.getUser())) {
      throw new IllegalArgumentException("Unauthorised to cancel booking");
    }
    if (booking.getCancelDate() != null) {
      throw new Exception("refund for this booking has already been processed");
    }

    booking.setCancelDate(new Date());
    Optional<Event> event = eventRepository.findById(booking.getEvent().getId());

    // Check if refund is whithin 48 hours of event start
    assert event.isPresent();
    boolean within48hrs = within48HoursOfEventStart(event.get());
    if (within48hrs) {
      throw new Exception("cannot refund within 48 hours of event start");
    }

    Refund processedRefund = new Refund(booking, new Date());
    long user_id = booking.getUser().getId();

    Payment payment = paymentRepository.findByBookingId(booking_id);
    double refundedAmount = payment.getAmount();

    // Retrieves current user that booked, and refund amount back to them.
    Optional<User> user = userRepository.findById(user_id);
    assert user.isPresent();
    double accountBalance = user.get().getAccountBalance();
    double penaltyFee = event.get().getCancellationFee();
    double updatedAccountBalance = accountBalance + refundedAmount - penaltyFee;
    user.get().setAccountBalance(updatedAccountBalance);
    userRepository.save(user.get());

    // Retrieve tickets booked and reallocates them to event ticket capacity
    int numOfTickets = ticketRepository.findTicketsByBooking(booking).size();
    int currentCapacity = event.get().getCapacity();
    event.get().setCapacity(numOfTickets + currentCapacity);
    eventRepository.save(event.get());

    refundRepository.save(processedRefund);
  }

  public static boolean within48HoursOfEventStart(Event event) {
    Date currentTime = new Date();
    Date eventTime = event.getDateTime();

    long timeDifference = Math.abs(currentTime.getTime() - eventTime.getTime());
    long hoursDifference = timeDifference / (1000 * 60 * 60);

    return hoursDifference < 48;
  }
}
