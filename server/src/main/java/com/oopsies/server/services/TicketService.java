package com.oopsies.server.services;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.TicketDTO;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.Ticket;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public TicketService() { }

    public List<TicketDTO> getAllTicketsForBooking(Booking booking) {
        List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TicketDTO> getAllTicketsForBooking(long booking_id) {
        Booking booking = bookingRepository.findBookingById(booking_id);
        List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<TicketDTO> getAllTicketsForUser(long user_id) {
        List<Booking> bookings = bookingRepository.findByUserId(user_id);
        List<Ticket> tickets = new ArrayList<>();
        for (Booking b : bookings) {
            tickets.addAll(ticketRepository.findTicketsByBooking(b));
        }
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void createNewTicket(Booking booking) {
        Ticket newTicket = new Ticket();
        newTicket.setBooking(booking);
        ticketRepository.save(newTicket);
    }

    public boolean validateTicket(long ticket_id) {
        Ticket ticket = ticketRepository.findTicketByTicketId(ticket_id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket doesn't exist");
        }
        boolean isRedeemed = ticket.isRedeemed();
        if (isRedeemed) {
            return false;
        }

        Booking booking = ticket.getBooking();
        if (booking.getCancelDate() != null) {
            return false;
        }
        Event event = booking.getEvent();
        Date eventDateTime = event.getDateTime();
        return isSameDateAndBeforeTime(new Date(), eventDateTime);
    }

    public boolean isSameDateAndBeforeTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean sameDate = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        if (sameDate) {
            return cal1.before(cal2);
        }

        return false;
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        Booking booking = ticket.getBooking();
        Event event = booking.getEvent();
        User user = booking.getUser();

        dto.setFirst_name(user.getFirstName());
        dto.setLast_name(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setEvent_dateTime(event.getDateTime());
        dto.setEvent_venue(event.getVenue());
        dto.setTicketPrice(event.getTicketPrice());
        dto.setBooking_id(booking.getBookingID());
        dto.setBooking_dateTime(booking.getBookingDate());
        dto.setTicket_id(ticket.getId());
        dto.setValid(validateTicket(ticket.getId()));
        return dto;
    }
}

