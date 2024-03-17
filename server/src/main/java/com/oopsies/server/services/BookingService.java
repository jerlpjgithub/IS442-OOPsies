package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.entity.*;
import com.oopsies.server.exception.UserInsufficientFundsException;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.util.DateUtil;
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

  @Autowired
  private RefundService refundService;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public BookingDTO createBooking(long user_id, long eventId, int numTickets) throws UserInsufficientFundsException {
    // totalGuests are the booker + their friends
    Optional<User> someUser = userDetailsService.getUserById(user_id);
    if (someUser.isEmpty()) {
      throw new IllegalArgumentException("User ID " + user_id + " cannot be found!");
    }

    // Find the event
    Optional<EventDTO> someEvent = eventService.getEventById(eventId);
    if (someEvent.isEmpty()) {
      throw new IllegalArgumentException("Event not found");
    }

    User user = someUser.get();

    boolean isOfficer = userDetailsService.isOfficer(user);

    EventDTO event = someEvent.get();

    if (!isOfficer) {
      DateUtil dateUtil = new DateUtil();
      Date eventDate = event.getDateTime();
      if (dateUtil.isLessThanTwentyFourHours(eventDate) || dateUtil.isMoreThanSixMonths(eventDate)) {
        throw new IllegalArgumentException("Customers can book tickets up to 6 months in advance and no later than 24 hours before the event start time");
      }
      if (numTickets > 5) {
        throw new IllegalArgumentException("Cannot purchase more than 5 tickets");
      }
    }
    if (event.getCapacity() < numTickets) {
      throw new IllegalArgumentException("Event capacity is less than number of guests");
    }

    // Reduce event capacity by numGuests + the og booker
    Booking booking = new Booking();
    booking.setUser(user);
    booking.setBookingDate(new Date());
    booking.setEventID(eventService.getEventFromDTO(event));
    booking.setCancelDate(null);
    Booking newBooking = bookingRepository.save(booking);
    eventService.updateEventCapacity(event, numTickets);

    for (int i = 0; i < numTickets; i++) {
      ticketService.createNewTicket(newBooking);
    }

    paymentService.processPayment(user, booking, event, numTickets);

    return convertToDTO(booking);
  }

  public List<BookingDTO> findBookingsByUserId(long userId) {
    List<Booking> bookings = bookingRepository.findByUserId(userId);
    return bookings.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public void processBookingRefund(long booking_id) throws Exception{
    refundService.processRefund(booking_id);
  }

  private BookingDTO convertToDTO(Booking booking) {
    BookingDTO dto = new BookingDTO();
    dto.setBookingID(booking.getBookingID());
    dto.setBookingDate(booking.getBookingDate());
    dto.setCancelDate(booking.getCancelDate());

    // get numTickets
    int numTickets = ticketService.getAllTicketsForBooking(booking).size();
    dto.setNumTickets(numTickets);
    Optional<EventDTO> event = eventService.getEventById(booking.getEventID().getId());
    assert event.isPresent();
    dto.setEvent(event.get());

    return dto;
  }
}
