package com.oopsies.server.entity;


import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "refund")
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int refundId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "booking_id", referencedColumnName = "bookingID")
  private Booking booking;

  // // OnetoOne with payment to get payment state related to booking 
  // @OneToOne
  // @Size(max = 50)
  // private int amount;

  @Temporal(TemporalType.DATE)
  @Column(name = "refundDate")
  private Date refundDate;


}
