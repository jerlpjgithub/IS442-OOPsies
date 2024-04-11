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
 * RefundService class is a service class that handles all the refund related operations.
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

  /**
   * cancelEvent is a method that cancels an event and refunds all the users that have booked tickets for the event.
   * @param event_id is the id of the event that is to be cancelled.
   * @throws IllegalArgumentException if the event requested is invalid or the user is unauthorised.
   */
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
  
  /**
   * processRefund is a method that processes a refund for a booking.
   * @param booking_id is the id of the booking that is to be refunded.
   * @throws Exception if the user is unauthorised, the refund has already been processed, or the refund is within 48 hours of the event start.
   */
  public void processRefund(Long booking_id) throws Exception {
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

  /**
   * within48HoursOfEventStart is a helper method that checks if the refund is within 48 hours of the event start.
   * @param event is the event that the refund is for.
   * @return boolean returns true if the refund is within 48 hours of the event start, false otherwise.
   */
  public static boolean within48HoursOfEventStart(Event event) {
    Date currentTime = new Date();
    Date eventTime = event.getDateTime();

    long timeDifference = Math.abs(currentTime.getTime() - eventTime.getTime());
    long hoursDifference = timeDifference / (1000 * 60 * 60);

    return hoursDifference < 48;
  }
}
