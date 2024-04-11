package com.oopsies.server.entity;

import java.util.Date;
import java.util.List;

/**
 * The EmailStructure class represents the structure of an email.
 * It includes the subject and message of the email.
 */
public class EmailStructure {

    private String message;
    private String subject;
    
    /**
     * Constructs a new EmailStructure with the specified details.
     *
     * @param name        the name of the recipient
     * @param email       the email of the recipient
     * @param bookingID   the booking ID
     * @param bookingDate the booking date
     * @param eventName   the event name
     * @param eventDate   the event date
     * @param refundDate  the refund date
     * @param venue       the venue of the event
     * @param ticketIDs   the list of ticket IDs
     * @param totalPrice  the total price of the tickets
     * @param penaltyFee  the penalty fee for refund
     * @param type        the type of the email ("Booking Confirmation" or "Refund Confirmation")
     */

    public EmailStructure(String name, String email, Long bookingID, Date bookingDate, 
                            String eventName, Date eventDate, 
                            Date refundDate, String venue, List<Long> ticketIDs, double totalPrice, double penaltyFee, String type) {

        if (type.equals("Booking Confirmation")) {
            this.subject = String.format("Event Booking Confirmation - Booking Number: %d", bookingID);
            this.message = String.format("Dear %s, " +
            "<br><br>We're thrilled to let you know that your booking for the concert has been successfully confirmed! Get ready for an unforgettable night of music and memories. Below are the details of your booking and your concert ticket information." +
            "<br><br><b>Booking Confirmation Details:</b><br>Booking Reference: %s<br>Contact Email: %s" +
            "<br><br><b>Event Details:</b> <br>Event Name: %s<br>Date: %s<br>Venue: %s"+
            "<br><br><b>Ticket Details:</b> <br>Ticket ID(s): %s<br>Total Price: %s" +
            "<br><br>Thank you for choosing OOPsies for your event experience. We hope you enjoy your event! <br><br>Yours Sincerely, <br>OOPsies", 
            name, bookingID, email, eventName, eventDate, venue, ticketIDs, totalPrice);

        } else if (type.equals("Refund Confirmation")) {
            this.subject = String.format("Refund Confirmation - Booking Number: %d", bookingID);
            this.message = String.format("Dear %s, " +
            "<br><br>We are writing to confirm the processing of your refund request for the booking referenced below. We understand that plans can change, and we're here to assist you through the process. Please find the details of your refund confirmation below." +
            "<br><br><b>Booking and Event Details:</b><br>Booking Reference: %s<br>Contact Email: %s" +
            "<br>Event Name: %s<br>Event Date: %s" +
            "<br><br><b>Refund Details:</b> <br>Refund Date: %s<br>Refunded Tickets: %s<br>Penalty Fee: %s<br>Refund Amount : %s" +
            "<br><br>We hope to have the opportunity to welcome you to one of our events in the future. Keep an eye on our website for updates on upcoming events! <br><br>Yours Sincerely, <br>OOPsies ", 
            name, bookingID, email, eventName, eventDate, refundDate, ticketIDs, penaltyFee, totalPrice);

        }
    }


    public String getMessage() {
        return this.message;

    }

    public String getSubject() {
        return this.subject;
    }
}
