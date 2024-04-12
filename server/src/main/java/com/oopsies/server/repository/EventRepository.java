package com.oopsies.server.repository;

import com.oopsies.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Event entities.
 * This interface defines several methods for querying event entities in the database.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Finds Event entity by Event id
     *
     * @param id The id of the Event we are finding
     * @return An Optional containing the Event entity if found, or an empty Optional if not found.
     */
    Optional<Event> findEventById(Long id);
    /**
     * Finds Event entity by Datetime, and Venue
     *
     * @param dateTime The date and time of the Event we are searching for
     * @param venue The venue of the Event we are searching for
     * @return An Optional containing the Event entity if found, or an empty Optional if not found.
     */
    Optional<Event> findEventByDateTimeAndVenue(Date dateTime, String venue);
    /**
     * Finds Event entity by manager id
     *
     * @param manager_id The manager id of the Event we are finding
     * @return An Optional containing the Event entity if found, or an empty Optional if not found.
     */
    List<Event> findEventsByManagerId(long manager_id);
}

