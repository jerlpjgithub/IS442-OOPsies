package com.oopsies.server.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;
import com.oopsies.server.dto.TicketDTO;

/**
 * Service for handling data for observing statistics
 * This service provides methods for retrieving the total number of
 * tickets sold, total revenue, potential attendance, and number of
 * refunds made
 */
@Service
public class DataService {

  @Autowired
  private TicketService ticketService;

  /**
   * Calculates the total number of tickets sold from a list of bookings.
   * If a booking has been cancelled (i.e., it has a cancellation date), it is not included in the total.
   *
   * @param bookingList The list of bookings to calculate the total number of tickets sold from.
   * @return The total number of tickets sold.
   */
  public int getTotalTicketsSold(List<BookingDTO> bookingList) {
    int totalTicketsSold = 0;

    for (BookingDTO booking : bookingList) {
      Date cancelDate = booking.getCancelDate();
      // System.out.println(cancelDate);
      if (cancelDate == null) {
        int numTickets = booking.getNumTickets();
        totalTicketsSold = totalTicketsSold + numTickets;
      }
    }

    return totalTicketsSold;
  }

  /**
   * Calculates the total revenue from a list of bookings for a specific event.
   * The total revenue is calculated as the total number of tickets sold times the ticket price.
   *
   * @param bookingList The list of bookings to calculate the total revenue from.
   * @param event The event to calculate the total revenue for.
   * @return The total revenue.
   */
  public double getTotalRevenue(List<BookingDTO> bookingList, EventDTO event) {
    double totalRevenue = 0.0;
    double ticketPrice = event.getTicketPrice();

    int totalTicketsSold = this.getTotalTicketsSold(bookingList);
    totalRevenue = totalTicketsSold * ticketPrice;

    return totalRevenue;
  }

  /**
   * Calculates the total number of attendees from a list of bookings.
   * An attendee is counted if they have redeemed their ticket.
   *
   * @param bookingList The list of bookings to calculate the total number of attendees from.
   * @return The total number of attendees.
   */
  public int getAttendance(List<BookingDTO> bookingList) {
    int numRedeemed = 0;

    for (BookingDTO booking : bookingList) {
      long bookingId = booking.getBookingID();
      List<TicketDTO> tickets = ticketService.getAllTicketsForBooking(bookingId);

      for (TicketDTO ticket : tickets) {
        if (ticket.isRedeemed()) {
          numRedeemed++;
        }
      }
    }

    return numRedeemed;
  }

  /**
   * Calculates the total number of refunds from a list of bookings.
   * A refund is counted if the booking has been cancelled.
   *
   * @param bookingList The list of bookings to calculate the total number of refunds from.
   * @return The total number of refunds.
   */
  public int getNumRefunds(List<BookingDTO> bookingList) {
    int numRefunds = 0;

    for (BookingDTO booking : bookingList) {
      Date cancelDate = booking.getCancelDate();
      if (cancelDate != null) {
        numRefunds += booking.getNumTickets();
      }
    }

    return numRefunds;
  }
}
