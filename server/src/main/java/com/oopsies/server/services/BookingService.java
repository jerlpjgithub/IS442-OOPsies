package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.entity.*;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EventRepository eventRepository;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }


  public Booking createBooking(long userId, long eventId, int numTickets) {
    // to satisfy Events' second biz req
    if (numTickets > 5) {
      throw new IllegalArgumentException("Cannot book more than 5 tickets");
    }
    
    Booking booking = new Booking();
    booking.setUserId(userId);

    // Fetch the event from the database
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException("Event not found"));

    booking.setEvent(eventId);
    event.setCapacity(event.getCapacity() - numTickets);
    booking.setNumTickets(numTickets);
    return bookingRepository.save(booking);
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
    return dto;
  }
}