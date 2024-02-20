package com.oopsies.server.entity;

import jakarta.persistence.*;
import java.util.*;

public class BookingDetails {
    private String eventName;
    private int numTickets;
    private int paymentDetails;

    public BookingDetails(String eventName, int numTickets, int paymentDetails) {
        this.eventName = eventName;
        this.numTickets = numTickets;
        this.paymentDetails = paymentDetails;
    }

    public List<Object> getBookingDetails() {
        List<Object> bookingDetails = new ArrayList<>();
        bookingDetails.add(eventName);
        bookingDetails.add(numTickets);
        bookingDetails.add(paymentDetails);
        return bookingDetails;
    }
}
