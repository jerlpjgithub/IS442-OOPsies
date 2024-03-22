package com.oopsies.server.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date paymentDate;

    public Payment(){ }

    public Payment(Booking booking, double amount,
                Date paymentDate) {
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
//         this.paymentStatus = paymentStatus;
    }

    // --------------- Getters and Setters (start) ------------------

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

//     public boolean isPaymentStatus() {
//         return paymentStatus;
//     }

//     public void setPaymentStatus(boolean paymentStatus) {
//         this.paymentStatus = paymentStatus;
//     }


    // --------------- Getters and Setters (end) ------------------

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", userId=" + booking.getBookingID() +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
//                 ", paymentStatus=" + paymentStatus +
                '}';
    }
}
