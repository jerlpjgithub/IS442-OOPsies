package com.oopsies.server.dto;

import java.util.Date;

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

  public EventCSVDTO() {
  }

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

  public Long getEventID() {
    return this.eventID;
  }

  public void setEventID(Long eventID) {
    this.eventID = eventID;
  }

  public String getEventName() {
    return this.eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getVenue() {
    return this.venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public Date getCancelDate() {
    return this.cancelDate;
  }

  public void setCancelDate(Date cancelDate) {
    this.cancelDate = cancelDate;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public double getCancellationFee() {
    return this.cancellationFee;
  }

  public void setCancellationFee(double cancellationFee) {
    this.cancellationFee = cancellationFee;
  }

  public double getTicketPrice() {
    return this.ticketPrice;
  }

  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  public int getTotalTicketsSold() {
    return this.totalTicketsSold;
  }

  public void setTotalTicketsSold(int totalTicketsSold) {
    this.totalTicketsSold = totalTicketsSold;
  }

  public double getTotalRevenue() {
    return this.totalRevenue;
  }

  public void setTotalRevenue(double totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  public int getAttendance() {
    return this.attendance;
  }

  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }

  public int getTotalTicketsRefunded() {
    return this.totalTicketsRefunded;
  }

  public void setTotalTicketsRefunded(int totalTicketsRefunded) {
    this.totalTicketsRefunded = totalTicketsRefunded;
  }

  public EventCSVDTO eventID(Long eventID) {
    setEventID(eventID);
    return this;
  }

  public EventCSVDTO eventName(String eventName) {
    setEventName(eventName);
    return this;
  }

  public EventCSVDTO dateTime(Date dateTime) {
    setDateTime(dateTime);
    return this;
  }

  public EventCSVDTO venue(String venue) {
    setVenue(venue);
    return this;
  }

  public EventCSVDTO cancelDate(Date cancelDate) {
    setCancelDate(cancelDate);
    return this;
  }

  public EventCSVDTO capacity(int capacity) {
    setCapacity(capacity);
    return this;
  }

  public EventCSVDTO cancellationFee(double cancellationFee) {
    setCancellationFee(cancellationFee);
    return this;
  }

  public EventCSVDTO ticketPrice(double ticketPrice) {
    setTicketPrice(ticketPrice);
    return this;
  }

  public EventCSVDTO totalTicketsSold(int totalTicketsSold) {
    setTotalTicketsSold(totalTicketsSold);
    return this;
  }

  public EventCSVDTO totalRevenue(double totalRevenue) {
    setTotalRevenue(totalRevenue);
    return this;
  }

  public EventCSVDTO attendance(int attendance) {
    setAttendance(attendance);
    return this;
  }

  public EventCSVDTO totalTicketsRefunded(int totalTicketsRefunded) {
    setTotalTicketsRefunded(totalTicketsRefunded);
    return this;
  }
}
