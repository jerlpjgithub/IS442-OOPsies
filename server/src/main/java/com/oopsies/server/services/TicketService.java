package com.oopsies.server.services;

import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.entity.Booking;
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

    public List<TicketDTO> getAllTicketsForBooking(Booking booking) {
        List<Ticket> tickets =  ticketRepository.findTicketsByBooking(booking);
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public long createNewTicket(Booking booking) {
        Ticket newTicket = new Ticket();
        newTicket.setBooking(booking);
        Ticket ticket = ticketRepository.save(newTicket);
        return ticket.getId();
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setBookingId(ticket.getBooking());
        return dto;
    }
}

