package com.oopsies.server.services;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.oopsies.server.dto.BookingDTO;
import com.oopsies.server.dto.EventDTO;

@Service
public class DataService {

    //to do : make sure it can handle refunds. Also might be simpler to do from eventService side.  (Done)
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

    public double getTotalRevenue(List<BookingDTO> bookingList, EventDTO event) {
        double totalRevenue = 0.0;
        double ticketPrice = event.getTicketPrice();

        int totalTicketsSold = this.getTotalTicketsSold(bookingList);
        totalRevenue = totalTicketsSold * ticketPrice;

        return totalRevenue;
    }


    
}
