package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.User;
import com.oopsies.server.entity.Event;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private EventService eventService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private TicketService ticketService;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public BookingDTO createBooking(long user_id, long eventId, int numTickets) throws UserInsufficientFundsException {
    // totalGuests are the booker + their friends
    Optional<User> user = userDetailsService.getUserById(user_id);
    if (user.isEmpty()) {
      throw new IllegalArgumentException("User ID " + user_id + " cannot be found!");
    }

    // Find the event
    Optional<EventDTO> someEvent = eventService.getEventById(eventId);
    if (someEvent.isEmpty()) {
      throw new IllegalArgumentException("Event not found");
    }

    if (numTickets > 5) {
      throw new IllegalArgumentException("Cannot purchase more than 5 tickets");
    }

    EventDTO event = someEvent.get();
    if (event.getCapacity() < numTickets) {
      throw new IllegalArgumentException("Event capacity is less than number of guests");
    }

    paymentService.processPayment(user.get(), event, numTickets);
    // Reduce event capacity by numGuests + the og booker
    Booking booking = new Booking();
    booking.setUser(user.get());
    booking.setBookingDate(new Date());
    booking.setEventID(eventService.getEventFromDTO(event));
    booking.setCancelDate(null);
    Booking newBooking = bookingRepository.save(booking);
    eventService.updateEventCapacity(event, numTickets);

    for (int i = 0; i < numTickets; i++) {
      ticketService.createNewTicket(newBooking);
    }
    return convertToDTO(booking);
  }

  // public List<Booking> findBookingsByUserId(long userId) {
  // return bookingRepository.findByUserId(userId);
  // }

  // public Booking getBookingByBookingId(int bookingId) {
  // return bookingRepository.findBookingByBookingID(bookingId);
  // }

  // public List<Booking> getBookingsByEventId(int eventId) {
  // return bookingRepository.findBookingsByEventID(eventId);
  // }

  public List<BookingDTO> findBookingsByUserId(long userId) {
    List<Booking> bookings = bookingRepository.findByUserId(userId);
    return bookings.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  private BookingDTO convertToDTO(Booking booking) {
    BookingDTO dto = new BookingDTO();
    dto.setBookingID(booking.getBookingID());
    dto.setBookingDate(booking.getBookingDate());
    dto.setCancelDate(booking.getCancelDate());

    // get numTickets
    int numTickets = ticketService.getAllTicketsForBooking(booking).size();
    dto.setNumTickets(numTickets);
    dto.setEvent(booking.getEventID());

    return dto;
  }
}
