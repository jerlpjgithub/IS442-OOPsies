package com.oopsies.server.dto;
import com.oopsies.server.entity.Event;

import java.util.Date;

public class BookingDTO {
    private Long bookingID;
    private Date bookingDate;
    private Date cancelDate;
    private int numTickets;
    private EventDTO event;


    // Constructor, getters, and setters
    public BookingDTO() {
    }

    public BookingDTO(long bookingID, Date bookingDate, Date cancelDate, int numTickets, EventDTO event) {
      this.bookingID = bookingID;
      this.bookingDate = bookingDate;
      this.cancelDate = cancelDate;
      this.numTickets = numTickets;
      this.event = event;
    }

    public long getBookingID() {
      return this.bookingID;
    }

    public void setBookingID(long bookingID) {
      this.bookingID = bookingID;
    }

    public Date getBookingDate() {
      return this.bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
      this.bookingDate = bookingDate;
    }

    public Date getCancelDate() {
      return this.cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
      this.cancelDate = cancelDate;
    }

    public BookingDTO bookingID(long bookingID) {
      setBookingID(bookingID);
      return this;
    }

    public BookingDTO bookingDate(Date bookingDate) {
      setBookingDate(bookingDate);
      return this;
    }

    public BookingDTO cancelDate(Date cancelDate) {
      setCancelDate(cancelDate);
      return this;
    }

    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }
}