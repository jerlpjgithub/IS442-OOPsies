package com.oopsies.server.payload.request;

import com.oopsies.server.entity.Booking;
import jakarta.validation.constraints.NotBlank;

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
}
