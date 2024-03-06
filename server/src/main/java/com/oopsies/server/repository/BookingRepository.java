package com.oopsies.server.repository;

import com.oopsies.server.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByBookingID(int bookingId);
    List<Booking> findBookingsByEventID (int eventId);
}

