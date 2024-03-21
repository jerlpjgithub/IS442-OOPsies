package com.oopsies.server.repository;

import com.oopsies.server.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingById(long id);

    List<Booking> findByUserIdAndEventId(long userId, long eventId);
    
    List<Booking> findByUserId(long userId);

    List<Booking> findByEventId(long event_id);
}

