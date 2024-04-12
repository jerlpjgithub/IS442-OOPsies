package com.oopsies.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * The TicketDTO class represents a ticket data transfer object.
 */
public class TicketDTO {
  @JsonProperty("userFirstMame")
  private String first_name;
  @JsonProperty("userLastName")
  private String last_name;
  @JsonProperty("userEmail")
  private String email;
  @JsonProperty("eventName")
  private String event_name;
  @JsonProperty("eventVenue")
  private String event_venue;
  @JsonProperty("eventDatetime")
  private Date event_dateTime;
  @JsonProperty("ticketId")
  private long ticket_id;
  @JsonProperty("ticketPrice")
  private double ticket_price;
  @JsonProperty("bookingID")
  private long booking_id;
  @JsonProperty("bookingDate")
  private Date booking_dateTime;
  @JsonProperty("redeemed")
  private boolean redeemed;
  @JsonProperty("isValid")
  private boolean isValid;

  /**
   * Default constructor for TicketDTO.
   */
  public TicketDTO() {
  }

  /**
   * Constructs a new TicketDTO with the specified values.
   *
   * @param first_name The first name of the user.
   * @param last_name The last name of the user.
   * @param email The email of the user.
   * @param event_name The name of the event.
   * @param event_venue The venue of the event.
   * @param event_dateTime The date and time of the event.
   * @param ticket_price The price of the ticket.
   * @param ticket_id The ID of the ticket.
   * @param booking_id The ID of the booking.
   * @param booking_dateTime The date and time of the booking.
   * @param isValid The validity of the ticket.
   * @param redeemed The redemption status of the ticket.
   */
  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEventName(String event_name) {
    this.event_name = event_name;
  }

  public void setEvent_venue(String event_venue) {
    this.event_venue = event_venue;
  }

  public void setEvent_dateTime(Date event_dateTime) {
    this.event_dateTime = event_dateTime;
  }

  public void setTicketPrice(double ticket_price) {
    this.ticket_price = ticket_price;
  }

  public void setTicket_id(long ticket_id) {
    this.ticket_id = ticket_id;
  }

  public void setBooking_id(long booking_id) {
    this.booking_id = booking_id;
  }

  public void setBooking_dateTime(Date booking_dateTime) {
    this.booking_dateTime = booking_dateTime;
  }

  public void setValid(boolean valid) {
    this.isValid = valid;
  }

  public void setRedeemed(boolean redeemed) {
    this.redeemed = redeemed;
  }

  public double getTicketPrice() {
    return this.ticket_price;
  }

  public long getTicket_id() {
    return this.ticket_id;
  }

  public long getBooking_id() {
    return this.booking_id;
  }

  public Date getBooking_dateTime() {
    return this.booking_dateTime;
  }

  public boolean isValid() {
    return this.isValid;
  }

  public boolean isRedeemed() {
    return this.redeemed;
  }
  

}
