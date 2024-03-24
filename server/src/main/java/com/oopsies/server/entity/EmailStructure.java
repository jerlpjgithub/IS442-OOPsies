package com.oopsies.server.entity;

import java.util.Date;

public class EmailStructure {

    private String message;
    private String subject;


    public EmailStructure(String name, String email, Long bookingID, Date bookingDate, 
                            String eventName, Date eventDate, 
                            Date refundDate, String venue, String type) {

        if (type.equals("Booking Confirmation")) {
            this.subject = String.format("Event Booking Confirmation - Booking Number: %d", bookingID);
            this.message = String.format("Dear %s, " +
            "\n\nWe're thrilled to let you know that your booking for the concert has been successfully confirmed! Get ready for an unforgettable night of music and memories. Below are the details of your booking and your concert ticket information." +
            "\n\nBooking Confirmation Details:\nBooking Reference: %s\nContact Email: %s" +
            "\n\nEvent Ticket Details: \nEvent Name: %s\nDate: %s\nVenue: %s"+
            "\n\nThank you for choosing OOPsies for your event experience. We hope you enjoy your event! \n\nYours Sincerely, \nOOPsies", name, bookingID, email, eventName, eventDate, venue);

        } else if (type.equals("Refund Confirmation")) {
            this.subject = String.format("Refund Confirmation - Booking Number: %d", bookingID);
            this.message = String.format("Dear %s, " +
            "\n\nWe are writing to confirm the processing of your refund request for the booking referenced below. We understand that plans can change, and we're here to assist you through the process. Please find the details of your refund confirmation below." +
            "\n\nBooking and Refund Details:\nBooking Reference: %s\nContact Email: %s" +
            "\nEvent Name: %s\nEvent Date: %s\nRefund Date: %s" +
            "\n\nWe hope to have the opportunity to welcome you to one of our events in the future. Keep an eye on our website for updates on upcoming events! \n\nYours Sincerely, \nOOPsies ", name, bookingID, email, eventName, eventDate, refundDate);

        }
    }


    public String getMessage() {
        return this.message;

    }

    public String getSubject() {
        return this.subject;
    }
}
