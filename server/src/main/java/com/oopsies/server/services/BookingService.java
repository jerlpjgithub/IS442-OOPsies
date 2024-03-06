package com.oopsies.server.services;

import com.oopsies.server.entity.Booking;
import com.oopsies.server.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public BookingService() { }

    public Booking getBookingByBookingId(int bookingId) {
        return bookingRepository.findBookingByBookingID(bookingId);
    }

    public List<Booking> getBookingsByEventId(int eventId) {
        return bookingRepository.findBookingsByEventID(eventId);
    }
}

