package com.oopsies.server.entity;

import java.util.Properties;
import java.text.SimpleDateFormat;
import javax.mail.*;


public class EmailSender {

    public void sendEmail(Booking booking) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String emailMessage = "Booking Details:\n"
                + "Booking ID: " + booking.getBookingID() + "\n"
                + "Event ID: " + booking.getEventID() + "\n"
                + "Ticket ID: " + booking.getTicketID() + "\n"
                + "Number of Tickets: " + booking.getNumTickets() + "\n"
                + "Booking Date: " + dateFormat.format(booking.getBookingDate()) + "\n"
                + "Cancel Date: " + dateFormat.format(booking.getCancelDate());

        // Here you would typically call a method to actually send the email.
        // This is just a placeholder.
        System.out.println("Sending email...\n" + emailMessage);
    }
}