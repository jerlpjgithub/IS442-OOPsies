package com.oopsies.server.repository;

import com.oopsies.server.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Repository interface for Booking entities.
 * This interface defines several methods for querying booking entities in the database.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    /**
     * Finds a Booking entity by its id.
     *
     * @param id The id of the Booking entity to find.
     * @return The Booking entity found
     */
    Booking findBookingById(long id);

    /**
     * Finds Booking entities made by the User for specific Event
     *
     * @param userId The id of the User whose Booking we are finding
     * @param eventId The id of the Events whose Booking we are finding
     * @return A List of Booking entities found
     */
    List<Booking> findByUserIdAndEventId(long userId, long eventId);

    /**
     * Finds Booking entities made by a User
     *
     * @param userId The id of the User whose Booking we are finding
     * @return A List of Booking entities found
     */
    List<Booking> findByUserId(long userId);

    /**
     * Finds Booking entities made for a specific event
     *
     * @param event_id The id of the Event being queried
     * @return A List of Booking entities found
     */
    List<Booking> findByEventId(long event_id);
}

