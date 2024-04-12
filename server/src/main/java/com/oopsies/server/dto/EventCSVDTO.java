package com.oopsies.server.dto;

import java.util.Date;

/**
 * A Data Transfer Object for Event CSV data.
 */
public class EventCSVDTO{
    private Long eventID;
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
     * Default constructor.
     */
  public EventCSVDTO() {
  }

    /**
     * Constructor with all parameters.
     */
  public EventCSVDTO(Long eventID, String eventName, Date dateTime, String venue, Date cancelDate, int capacity, double cancellationFee, double ticketPrice, int totalTicketsSold, double totalRevenue, int attendance, int totalTicketsRefunded) {
    this.eventID = eventID;
    this.eventName = eventName;
    this.dateTime = dateTime;
    this.venue = venue;
    this.cancelDate = cancelDate;
    this.capacity = capacity;
    this.cancellationFee = cancellationFee;
    this.ticketPrice = ticketPrice;
    this.totalTicketsSold = totalTicketsSold;
    this.totalRevenue = totalRevenue;
    this.attendance = attendance;
    this.totalTicketsRefunded = totalTicketsRefunded;
  }

    /**
     * Returns the event ID.
     *
     * @return The event ID.
     */
  public Long getEventID() {
    return this.eventID;
  }

    /**
     * Sets the event ID.
     *
     * @param eventID The event ID.
     */
  public void setEventID(Long eventID) {
    this.eventID = eventID;
  }

    /**
     * Returns the event name.
     *
     * @return The event name.
     */
  public String getEventName() {
    return this.eventName;
  }

    /**
     * Sets the event name.
     *
     * @param eventName The event name.
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
    return this.dateTime;
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
    return this.venue;
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
  public Date getCancelDate() {
    return this.cancelDate;
  }


   /**
     * Sets the cancellation date of the event.
     *
     * @param cancelDate The cancellation date of the event.
     */
  public void setCancelDate(Date cancelDate) {
    this.cancelDate = cancelDate;
  }

    /**
     * Returns the capacity of the event.
     *
     * @return The capacity of the event.
     */
  public int getCapacity() {
    return this.capacity;
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
    return this.cancellationFee;
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
    return this.ticketPrice;
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
     * Returns the total number of tickets sold for the event.
     *
     * @return The total number of tickets sold for the event.
     */
  public int getTotalTicketsSold() {
    return this.totalTicketsSold;
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
     * Returns the total revenue of the event.
     *
     * @return The total revenue of the event.
     */
  public double getTotalRevenue() {
    return this.totalRevenue;
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
    return this.attendance;
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
    return this.totalTicketsRefunded;
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
     * Sets the event ID and returns this object.
     *
     * @param eventID The event ID.
     * @return This object.
     */
  public EventCSVDTO eventID(Long eventID) {
    setEventID(eventID);
    return this;
  }

      /**
     * Sets the event name and returns this object.
     *
     * @param eventName The event name.
     * @return This object.
     */
  public EventCSVDTO eventName(String eventName) {
    setEventName(eventName);
    return this;
  }

      /**
     * Sets the date and time of the event and returns this object.
     *
     * @param dateTime The date and time of the event.
     * @return This object.
     */
  public EventCSVDTO dateTime(Date dateTime) {
    setDateTime(dateTime);
    return this;
  }

      /**
     * Sets the venue of the event and returns this object.
     *
     * @param venue The venue of the event.
     * @return This object.
     */
  public EventCSVDTO venue(String venue) {
    setVenue(venue);
    return this;
  }

      /**
     * Sets the cancellation date of the event and returns this object.
     *
     * @param cancelDate The cancellation date of the event.
     * @return This object.
     */
  public EventCSVDTO cancelDate(Date cancelDate) {
    setCancelDate(cancelDate);
    return this;
  }

      /**
     * Sets the capacity of the event and returns this object.
     *
     * @param capacity The capacity of the event.
     * @return This object.
     */
  public EventCSVDTO capacity(int capacity) {
    setCapacity(capacity);
    return this;
  }

      /**
     * Sets the cancellation fee of the event and returns this object.
     *
     * @param cancellationFee The cancellation fee of the event.
     * @return This object.
     */
  public EventCSVDTO cancellationFee(double cancellationFee) {
    setCancellationFee(cancellationFee);
    return this;
  }

      /**
     * Sets the ticket price of the event and returns this object.
     *
     * @param ticketPrice The ticket price of the event.
     * @return This object.
     */
  public EventCSVDTO ticketPrice(double ticketPrice) {
    setTicketPrice(ticketPrice);
    return this;
  }

      /**
     * Sets the total number of tickets sold for the event and returns this object.
     *
     * @param totalTicketsSold The total number of tickets sold for the event.
     * @return This object.
     */
  public EventCSVDTO totalTicketsSold(int totalTicketsSold) {
    setTotalTicketsSold(totalTicketsSold);
    return this;
  }

      /**
     * Sets the total revenue of the event and returns this object.
     *
     * @param totalRevenue The total revenue of the event.
     * @return This object.
     */
  public EventCSVDTO totalRevenue(double totalRevenue) {
    setTotalRevenue(totalRevenue);
    return this;
  }

      /**
     * Sets the attendance of the event and returns this object.
     *
     * @param attendance The attendance of the event.
     * @return This object.
     */
  public EventCSVDTO attendance(int attendance) {
    setAttendance(attendance);
    return this;
  }

      /**
     * Sets the total number of tickets refunded for the event and returns this object.
     *
     * @param totalTicketsRefunded The total number of tickets refunded for the event.
     * @return This object.
     */
  public EventCSVDTO totalTicketsRefunded(int totalTicketsRefunded) {
    setTotalTicketsRefunded(totalTicketsRefunded);
    return this;
  }
}
