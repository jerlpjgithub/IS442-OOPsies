package com.oopsies.server.dto;

import java.util.Date;

public class CsvDTO {
    private Long bookingID;
    private Date bookingDate;
    private Date cancelDate;
    private String fullName;
    private String email;
    private int numOfTickets;


    // Constructor, getters, and setters
    public CsvDTO() {
    }

    public CsvDTO(long bookingID, Date bookingDate, Date cancelDate, String fullName, String email, int numOfTickets) {
      this.bookingID = bookingID;
      this.bookingDate = bookingDate;
      this.cancelDate = cancelDate;
      this.fullName = fullName;
      this.email = email;
      this.numOfTickets = numOfTickets;
    }


  public Long getBookingID() {
    return this.bookingID;
  }

  public void setBookingID(Long bookingID) {
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

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getNumOfTickets(){
    return this.numOfTickets;
  }

  public void setNumOfTickets(int numOfTickets){
    this.numOfTickets = numOfTickets;
  }

  public String getEmail() {
    return this.email;
  }
}
