package com.oopsies.server.dto;
import java.util.Date;

public class BookingDTO {
    private long bookingID;
    private Date bookingDate;
    private Date cancelDate;

    // Constructor, getters, and setters

    public BookingDTO() {
    }

    public BookingDTO(long bookingID, Date bookingDate, Date cancelDate) {
      this.bookingID = bookingID;
      this.bookingDate = bookingDate;
      this.cancelDate = cancelDate;
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
}