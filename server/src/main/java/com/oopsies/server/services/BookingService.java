package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.BookingRepository;
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

    public BookingService(BookingRepository bookingRepository) { 
      this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(long user_id, Booking booking){
      // Fetch the user based on the provided user_id
      User user = userRepository.getReferenceById(user_id);

      // Set the fetched user in the booking entity
      booking.setUser(user);

      // Now you can save the booking entity
      return bookingRepository.save(booking);
    }

    // public List<Booking> findBookingsByUserId(long userId) {
    //     return bookingRepository.findByUserId(userId);
    // }

    // public Booking getBookingByBookingId(int bookingId) {
    //     return bookingRepository.findBookingByBookingID(bookingId);
    // }

    // public List<Booking> getBookingsByEventId(int eventId) {
    //     return bookingRepository.findBookingsByEventID(eventId);
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

