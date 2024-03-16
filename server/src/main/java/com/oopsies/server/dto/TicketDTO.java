package com.oopsies.server.dto;

import com.oopsies.server.entity.Booking;

public class TicketDTO {
    Booking booking;

    public TicketDTO() { }

    public Booking getBookingId() {
        return booking;
    }

    public void setBookingId(Booking booking) {
        this.booking = booking;
    }

    public TicketDTO booking(Booking booking) {
        setBookingId(booking);
        return this;
    }
}
