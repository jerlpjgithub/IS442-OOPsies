package com.oopsies.server.dto;

import java.util.Date;

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

  public EventDTO() {
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getVenue() {
    return venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public Date getCancelDate(){
    return this.cancelDate;
  }

  public boolean isEventCancelled() {
    return cancelDate != null;
  }

  public void setEventCancelled(Date cancelDate) {
    this.cancelDate = cancelDate;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public double getCancellationFee() {
    return cancellationFee;
  }

  public void setCancellationFee(double cancellationFee) {
    this.cancellationFee = cancellationFee;
  }

  public double getTicketPrice() {
    return ticketPrice;
  }

  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  public double getTotalRevenue() {
    return totalRevenue;
  }

  public void setTotalRevenue(double totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  public int getAttendance() {
    return attendance;
  }

  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }

  public int getTotalTicketsRefunded() {
    return totalTicketsRefunded;
  }

  public void setTotalTicketsRefunded(int totalTicketsRefunded) {
    this.totalTicketsRefunded = totalTicketsRefunded;
  }

  public int getTotalTicketsSold() {
    return totalTicketsSold;
  }

  public void setTotalTicketsSold(int totalTicketsSold) {
    this.totalTicketsSold = totalTicketsSold;
  }

  public EventDTO eventID(long eventId) {
    setId(eventId);
    return this;
  }

  public EventDTO eventName(String eventName) {
    setEventName(eventName);
    return this;
  }

  public EventDTO dateTime(Date dateTime) {
    setDateTime(dateTime);
    return this;
  }

  public EventDTO venue(String venue) {
    setVenue(venue);
    return this;
  }

  public EventDTO cancelDate(Date cancelDate) {
    setEventCancelled(cancelDate);
    return this;
  }

  public EventDTO capacity(int capacity) {
    setCapacity(capacity);
    return this;
  }

  public EventDTO cancellationFee(double cancellationFee) {
    setCancellationFee(cancellationFee);
    return this;
  }

  public EventDTO ticketPrice(double ticketPrice) {
    setTicketPrice(ticketPrice);
    return this;
  }

  public EventDTO totalTicketsSold(int totalTicketsSold) {
    setTotalTicketsSold(totalTicketsSold);
    return this;
  }
}
