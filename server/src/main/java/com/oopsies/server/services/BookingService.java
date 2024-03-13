package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.User;
import com.oopsies.server.entity.Event;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.repository.EventRepository;
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

  public Booking createBooking(long user_id, Booking booking, int numGuests) {
    if (numGuests > 4) {
      throw new IllegalArgumentException("Cannot purchase more than 5 tickets");
    }
    // totalGuests are the booker + their friends
    int totalGuests = numGuests + 1;
    User user = userRepository.getReferenceById(user_id);
    Long eventId = booking.getEventID();
    if (eventId == null) {
      throw new IllegalArgumentException("Event ID cannot be null");
    }
    // Find the event
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    if (event.getCapacity() < (totalGuests)) {
      throw new IllegalArgumentException("Event capacity is less than number of guests");
    }
    // Reduce event capacity by numGuests + the og booker
    event.setCapacity(event.getCapacity() - (totalGuests));
    booking.setUser(user);
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
