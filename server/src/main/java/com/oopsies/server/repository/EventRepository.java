package com.oopsies.server.repository;

import com.oopsies.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventById(Long id);

    Optional<Event> findEventByEventNameAndDateTimeAndVenue(String eventName, Date dateTime, String venue);
}

