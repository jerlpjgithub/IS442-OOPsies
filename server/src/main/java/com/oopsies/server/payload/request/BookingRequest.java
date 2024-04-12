package com.oopsies.server.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Payload for Booking requests
 * Contains eventId and number of tickets being bought
 */
public class BookingRequest {
    @NotBlank
    private long eventId;

    @NotBlank
    private int numTickets;

    public BookingRequest(long eventId, int numTickets) {
        this.eventId = eventId;
        this.numTickets = numTickets;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "eventId=" + eventId +
                ", numTickets=" + numTickets +
                '}';
    }
}
