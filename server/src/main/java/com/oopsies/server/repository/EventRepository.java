package com.oopsies.server.repository;

import com.oopsies.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventByEventID(int eventId);
}

