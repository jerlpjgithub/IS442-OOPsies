package com.oopsies.server.services;

import com.oopsies.server.entity.Ticket;
import com.oopsies.server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTicketsForEvent(int eventId) {
        return ticketRepository.findTicketsByEventId(eventId);
    }
}

