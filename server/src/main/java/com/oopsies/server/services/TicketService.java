package com.oopsies.server.services;

import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketService() { }

    public List<TicketDTO> getAllTicketsForBooking(int bookingId) {
        List<Ticket> tickets =  ticketRepository.findTicketsByBookingId(bookingId);
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public long createNewTicket(long bookingId) {
        Ticket newTicket = new Ticket();
        newTicket.setBookingId(bookingId);
        Ticket ticket = ticketRepository.save(newTicket);
        return ticket.getId();
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setBookingId(ticket.getBookingId());
        return dto;
    }
}

