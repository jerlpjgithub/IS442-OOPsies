package com.oopsies.server.repository;

import com.oopsies.server.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByBookingID(int bookingId);
    
    List<Booking> findByUserId(long userId);
    // List<Booking> findByAll(long userId);
    // List<Booking> findBookingsByEventID (int eventId);
}

