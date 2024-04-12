package com.oopsies.server.dto;

import java.util.Date;

/**
 * The CsvDTO class represents a CSV data transfer object.
 * It contains information about a booking, including the booking ID, booking date, cancellation date, full name, email, number of tickets, and number of redeemed tickets.
 */
public class CsvDTO {
    private Long bookingID;
    private Date bookingDate;
    private Date cancelDate;
    private String fullName;
    private String email;
    private int numOfTickets;
    private int numOfRedeemedTickets; 


    /**
     * Default constructor for CsvDTO.
     */    
    public CsvDTO() {
    }

    /**
     * Constructs a new CsvDTO with the specified values.
     *
     * @param bookingID The ID of the booking.
     * @param bookingDate The date of the booking.
     * @param cancelDate The cancellation date of the booking.
     * @param fullName The full name of the person who made the booking.
     * @param email The email of the person who made the booking.
     * @param numOfTickets The number of tickets in the booking.
     * @param numOfRedeemedTickets The number of redeemed tickets in the booking.
     */
    public CsvDTO(long bookingID, Date bookingDate, Date cancelDate, String fullName, String email, int numOfTickets, int numOfRedeemedTickets) {
      this.bookingID = bookingID;
      this.bookingDate = bookingDate;
      this.cancelDate = cancelDate;
      this.fullName = fullName;
      this.email = email;
      this.numOfTickets = numOfTickets;
      this.numOfRedeemedTickets = numOfRedeemedTickets;
    }

  /**
   * Returns the ID of the booking.
   *
   * @return The ID of the booking.
   */
  public Long getBookingID() {
    return this.bookingID;
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
     * Returns the date of the booking.
     *
     * @return The date of the booking.
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
     * Returns the cancellation date of the booking.
     *
     * @return The cancellation date of the booking.
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
     * Returns the full name of the person who made the booking.
     *
     * @return The full name of the person who made the booking.
     */
  public String getFullName() {
    return this.fullName;
  }

    /**
     * Sets the full name of the person who made the booking.
     *
     * @param fullName The full name of the person who made the booking.
     */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

    /**
     * Returns the number of tickets in the booking.
     *
     * @return The number of tickets in the booking.
     */
  public int getNumOfTickets(){
    return this.numOfTickets;
  }

    /**
     * Sets the number of tickets in the booking.
     *
     * @param numOfTickets The number of tickets in the booking.
     */
  public void setNumOfTickets(int numOfTickets){
    this.numOfTickets = numOfTickets;
  }

    /**
     * Returns the email of the person who made the booking.
     *
     * @return The email of the person who made the booking.
     */
  public String getEmail() {
    return this.email;
  }

    /**
     * Returns the number of redeemed tickets in the booking.
     *
     * @return The number of redeemed tickets in the booking.
     */
  public int getNumOfRedeemedTickets(){
    return this.numOfRedeemedTickets;
  }
}
