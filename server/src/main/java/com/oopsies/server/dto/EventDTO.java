package com.oopsies.server.dto;

import java.util.Date;
/**
 * The EventDTO class represents an event data transfer object.
 */
public class EventDTO {
  private Long id;
  private String eventName;
  private Date dateTime;
  private String venue;
  private Date cancelDate;
  private int capacity;
  private double cancellationFee;
  private double ticketPrice;
  private int totalTicketsSold;
  private double totalRevenue;
  private int attendance;
  private int totalTicketsRefunded;

  /**
   * Default constructor for EventDTO.
   */
  public EventDTO() {
  }

  /**
   * Constructs a new EventDTO with the specified values.
   *
   * @param id The ID of the event.
   * @param eventName The name of the event.
   * @param dateTime The date and time of the event.
   * @param venue The venue of the event.
   * @param cancelDate The cancellation date of the event.
   * @param capacity The capacity of the event.
   * @param cancellationFee The cancellation fee of the event.
   * @param ticketPrice The ticket price of the event.
   * @param totalTicketsSold The total number of tickets sold for the event.
   */
  public EventDTO(Long id, String eventName, Date dateTime, String venue, Date cancelDate, int capacity,
      double cancellationFee, double ticketPrice, int totalTicketsSold) {
    this.id = id;
    this.eventName = eventName;
    this.dateTime = dateTime;
    this.venue = venue;
    this.cancelDate = cancelDate;
    this.capacity = capacity;
    this.cancellationFee = cancellationFee;
    this.ticketPrice = ticketPrice;
    this.totalTicketsSold = totalTicketsSold;
  }

  /**
   * Returns the ID of the event.
   *
   * @return The ID of the event.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the event.
   *
   * @param id The ID of the event.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the name of the event.
   *
   * @return The name of the event.
   */
  public String getEventName() {
    return eventName;
  }

  /**
   * Sets the name of the event.
   *
   * @param eventName The name of the event.
   */
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * Returns the date and time of the event.
   *
   * @return The date and time of the event.
   */
  public Date getDateTime() {
    return dateTime;
  }

  /**
   * Sets the date and time of the event.
   *
   * @param dateTime The date and time of the event.
   */
  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * Returns the venue of the event.
   *
   * @return The venue of the event.
   */
  public String getVenue() {
    return venue;
  }

  /**
   * Sets the venue of the event.
   *
   * @param venue The venue of the event.
   */
  public void setVenue(String venue) {
    this.venue = venue;
  }

  /**
   * Returns the cancellation date of the event.
   *
   * @return The cancellation date of the event.
   */
  public Date getCancelDate(){
    return this.cancelDate;
  }

  /**
   * Returns whether the event is cancelled.
   *
   * @return True if the event is cancelled, false otherwise.
   */
  public boolean isEventCancelled() {
    return cancelDate != null;
  }

  /**
   * Sets the cancellation date of the event.
   *
   * @param cancelDate The cancellation date of the event.
   */
  public void setEventCancelled(Date cancelDate) {
    this.cancelDate = cancelDate;
  }

  /**
   * Returns the capacity of the event.
   *
   * @return The capacity of the event.
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Sets the capacity of the event.
   *
   * @param capacity The capacity of the event.
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Returns the cancellation fee of the event.
   *
   * @return The cancellation fee of the event.
   */
  public double getCancellationFee() {
    return cancellationFee;
  }

  /**
   * Sets the cancellation fee of the event.
   *
   * @param cancellationFee The cancellation fee of the event.
   */
  public void setCancellationFee(double cancellationFee) {
    this.cancellationFee = cancellationFee;
  }

  /**
   * Returns the ticket price of the event.
   *
   * @return The ticket price of the event.
   */
  public double getTicketPrice() {
    return ticketPrice;
  }

  /**
   * Sets the ticket price of the event.
   *
   * @param ticketPrice The ticket price of the event.
   */
  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  /**
   * Returns the total revenue of the event.
   *
   * @return The total revenue of the event.
   */
  public double getTotalRevenue() {
    return totalRevenue;
  }

  /**
   * Sets the total revenue of the event.
   *
   * @param totalRevenue The total revenue of the event.
   */
  public void setTotalRevenue(double totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  /**
   * Returns the attendance of the event.
   *
   * @return The attendance of the event.
   */
  public int getAttendance() {
    return attendance;
  }

  /**
   * Sets the attendance of the event.
   *
   * @param attendance The attendance of the event.
   */
  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }

  /**
   * Returns the total number of tickets refunded for the event.
   *
   * @return The total number of tickets refunded for the event.
   */
  public int getTotalTicketsRefunded() {
    return totalTicketsRefunded;
  }

  /**
   * Sets the total number of tickets refunded for the event.
   *
   * @param totalTicketsRefunded The total number of tickets refunded for the event.
   */
  public void setTotalTicketsRefunded(int totalTicketsRefunded) {
    this.totalTicketsRefunded = totalTicketsRefunded;
  }

  /**
   * Returns the total number of tickets sold for the event.
   *
   * @return The total number of tickets sold for the event.
   */
  public int getTotalTicketsSold() {
    return totalTicketsSold;
  }

  /**
   * Sets the total number of tickets sold for the event.
   *
   * @param totalTicketsSold The total number of tickets sold for the event.
   */
  public void setTotalTicketsSold(int totalTicketsSold) {
    this.totalTicketsSold = totalTicketsSold;
  }

  /**
   * Sets the event ID and returns the EventDTO object.
   * @param eventId The ID of the event.
   */
  public EventDTO eventID(long eventId) {
    setId(eventId);
    return this;
  }

  /**
   * Sets the name of the event and returns the EventDTO object.
   * @param eventName The name of the event.
   */
  public EventDTO eventName(String eventName) {
    setEventName(eventName);
    return this;
  }

  /**
   * Sets the date and time of the event and returns the EventDTO object.
   * @param dateTime The date and time of the event.
   */
  public EventDTO dateTime(Date dateTime) {
    setDateTime(dateTime);
    return this;
  }

  /**
   * Sets the venue of the event and returns the EventDTO object.
   * @param venue The venue of the event.
   */
  public EventDTO venue(String venue) {
    setVenue(venue);
    return this;
  }

  /**
   * Sets the cancellation date of the event and returns the EventDTO object.
   * @param cancelDate The cancellation date of the event.
   */
  public EventDTO cancelDate(Date cancelDate) {
    setEventCancelled(cancelDate);
    return this;
  }

  /**
   * Sets the capacity of the event and returns the EventDTO object.
   * @param capacity The capacity of the event.
   */
  public EventDTO capacity(int capacity) {
    setCapacity(capacity);
    return this;
  }

  /**
   * Sets the cancellation fee of the event and returns the EventDTO object.
   * @param cancellationFee The cancellation fee of the event.
   */
  public EventDTO cancellationFee(double cancellationFee) {
    setCancellationFee(cancellationFee);
    return this;
  }

  /**
   * Sets the ticket price of the event and returns the EventDTO object.
   * @param ticketPrice The ticket price of the event.
   */
  public EventDTO ticketPrice(double ticketPrice) {
    setTicketPrice(ticketPrice);
    return this;
  }

  /**
   * Sets the total tickets sold of the event and returns the EventDTO object.
   * @param totalTicketsSold The total tickets sold of the event.
   */
  public EventDTO totalTicketsSold(int totalTicketsSold) {
    setTotalTicketsSold(totalTicketsSold);
    return this;
  }
}
