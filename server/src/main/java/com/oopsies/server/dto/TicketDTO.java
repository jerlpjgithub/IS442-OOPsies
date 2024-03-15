package com.oopsies.server.dto;

public class TicketDTO {
    long bookingId;

    public TicketDTO() { }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public TicketDTO bookingId(long bookingId) {
        setBookingId(bookingId);
        return this;
    }
}
