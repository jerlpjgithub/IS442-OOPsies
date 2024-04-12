package com.oopsies.server.repository;

import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Ticket entities.
 * This interface defines several methods for querying ticket entities in the database.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * Finds Ticket entity by booking
     *
     * @param booking the Booking entity by which we are searching for booked tickets
     * @return A List of Tickets corresponding to the queried Booking
     */
    List<Ticket> findTicketsByBooking(Booking booking);
    /**
     * Finds Ticket entity by Ticket id
     *
     * @param ticketId The id of the Ticket we are finding
     * @return THe Ticket entity being found
     */
    Ticket findTicketByTicketId(long ticketId);
}

