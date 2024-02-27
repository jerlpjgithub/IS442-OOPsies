package com.oopsies.server.repository;

import com.oopsies.server.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findTicketsByEventId(int eventId);
}

