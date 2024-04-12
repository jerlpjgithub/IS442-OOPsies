package com.oopsies.server.dto;

import java.util.Date;

/**
 * The BookingDTO class represents a booking data transfer object.
 * It contains information about a booking, including the booking ID, booking date, cancellation date, number of tickets, and the event.
 */
public class BookingDTO {
    private Long bookingID;
    private Date bookingDate;
    private Date cancelDate;
    private int numTickets;
    private EventDTO event;


    /**
     * Default constructor for BookingDTO.
     */    
    public BookingDTO() {
    }

    /**
     * Constructs a new BookingDTO with the specified values.
     *
     * @param bookingID The ID of the booking.
     * @param bookingDate The date of the booking.
     * @param cancelDate The cancellation date of the booking.
     * @param numTickets The number of tickets in the booking.
     * @param event The event associated with the booking.
     */
    public BookingDTO(long bookingID, Date bookingDate, Date cancelDate, int numTickets, EventDTO event) {
      this.bookingID = bookingID;
      this.bookingDate = bookingDate;
      this.cancelDate = cancelDate;
      this.numTickets = numTickets;
      this.event = event;
    }

    /**
     * Returns the ID of the booking.
     *
     * @return The ID of the booking.
     */
    public long getBookingID() {
      return this.bookingID;
    }

    /**
     * Sets the ID of the booking.
     *
     * @param bookingID The ID of the booking.
     */
    public void setBookingID(long bookingID) {
      this.bookingID = bookingID;
    }

      /**
   * Returns the Date of the booking.
   *
   * @return The Date of the booking.
   */
    public Date getBookingDate() {
      return this.bookingDate;
    }

    /**
     * Sets the date of the booking.
     *
     * @param bookingDate The date of the booking.
     */
    public void setBookingDate(Date bookingDate) {
      this.bookingDate = bookingDate;
    }

   /**
     * Returns the cancel date.
     *
     * @return The date of the cancellation.
     */
    public Date getCancelDate() {
      return this.cancelDate;
    }

    /**
     * Sets the cancellation date of the booking.
     *
     * @param cancelDate The cancellation date of the booking.
     */
    public void setCancelDate(Date cancelDate) {
      this.cancelDate = cancelDate;
    }

    /**
     * Sets the ID of the booking and returns this BookingDTO.
     *
     * @param bookingID The ID of the booking.
     * @return This BookingDTO.
     */
    public BookingDTO bookingID(long bookingID) {
      setBookingID(bookingID);
      return this;
    }

    /**
     * Sets the date of the booking and returns this BookingDTO.
     *
     * @param bookingDate The date of the booking.
     * @return This BookingDTO.
     */
    public BookingDTO bookingDate(Date bookingDate) {
      setBookingDate(bookingDate);
      return this;
    }

    /**
     * Sets the cancellation date of the booking and returns this BookingDTO.
     *
     * @param cancelDate The cancellation date of the booking.
     * @return This BookingDTO.
     */
    public BookingDTO cancelDate(Date cancelDate) {
      setCancelDate(cancelDate);
      return this;
    }

    /**
     * Sets the ID of the booking.
     *
     * @param bookingID The ID of the booking.
     */
    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * Returns the number of tickets in the booking.
     *
     * @return The number of tickets in the booking.
     */
    public int getNumTickets() {
        return numTickets;
    }

    /**
     * Sets the number of tickets in the booking.
     *
     * @param numTickets The number of tickets in the booking.
     */
    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    /**
     * Returns the event associated with the booking.
     *
     * @return The event associated with the booking.
     */
    public EventDTO getEvent() {
        return event;
    }

    /**
     * Sets the event associated with the booking.
     *
     * @param event The event associated with the booking.
     */
    public void setEvent(EventDTO event) {
        this.event = event;
    }
}