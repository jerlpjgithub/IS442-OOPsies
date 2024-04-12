package com.oopsies.server.services;

import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.TicketRepository;
import com.oopsies.server.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for handling Ticket entities
 * This service contains methods for retrieving Ticket information
 * by booking or by user, creating new tickets, validating and redeeming
 * Tickets
 */
@Service
public class TicketService {

  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private BookingRepository bookingRepository;

  public TicketService() {
  }

  public List<TicketDTO> getAllTicketsForBooking(Booking booking) {
    List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);
    return tickets.stream()
        .map(this::convertToDTO)
        .toList();
  }

  public List<TicketDTO> getAllTicketsForBooking(long booking_id) {
    Booking booking = bookingRepository.findBookingById(booking_id);
    List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);
    return tickets.stream()
        .map(this::convertToDTO)
        .toList();
  }

  public List<TicketDTO> getAllTicketsForUser(long user_id) {
    List<Booking> bookings = bookingRepository.findByUserId(user_id);
    List<Ticket> tickets = new ArrayList<>();
    for (Booking b : bookings) {
      tickets.addAll(ticketRepository.findTicketsByBooking(b));
    }
    return tickets.stream()
        .map(this::convertToDTO)
        .toList();
  }

  public void createNewTicket(Booking booking) {
    Ticket newTicket = new Ticket();
    newTicket.setBooking(booking);
    ticketRepository.save(newTicket);
  }

  public boolean validateTicket(long ticket_id) {
    Ticket ticket = ticketRepository.findTicketByTicketId(ticket_id);

    // Check if ticket exists
    if (ticket == null) {
      throw new IllegalArgumentException("Ticket doesn't exist");
    }

    // Check if the ticket has already been redeemed
    boolean isRedeemed = ticket.isRedeemed();
    if (isRedeemed) {
      throw new IllegalArgumentException("Ticket has already been redeemed");
    }

    // Check if the booking has already been cancelled
    Booking booking = ticket.getBooking();
    if (booking.getCancelDate() != null) {
      throw new IllegalArgumentException("Booking has been cancelled");
    }
    Event event = booking.getEvent();
    Date eventDateTime = event.getDateTime();

    // Check if the event has already passed
    if (!new DateUtil().isSameDateAndBeforeTime(new Date(), eventDateTime)) {
      throw new IllegalArgumentException("Event has either passed or not started yet!");
    }

    // All is good, redeem the ticket and return true
    ticket.setRedeemed(true);
    ticketRepository.save(ticket);

    return true;
  }

  private TicketDTO convertToDTO(Ticket ticket) {
    TicketDTO dto = new TicketDTO();
    Booking booking = ticket.getBooking();
    Event event = booking.getEvent();
    User user = booking.getUser();

    dto.setFirst_name(user.getFirstName());
    dto.setLast_name(user.getLastName());
    dto.setEmail(user.getEmail());
    dto.setEventName(event.getEventName());
    dto.setEvent_dateTime(event.getDateTime());
    dto.setEvent_venue(event.getVenue());
    dto.setTicketPrice(event.getTicketPrice());
    dto.setBooking_id(booking.getBookingID());
    dto.setBooking_dateTime(booking.getBookingDate());
    dto.setTicket_id(ticket.getId());
    // dto.setValid(validateTicket(ticket.getId()));
    dto.setRedeemed(ticket.isRedeemed());

    return dto;
  }
}
