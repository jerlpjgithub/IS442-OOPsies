package com.oopsies.server.entity;

import com.oopsies.server.exception.UserInsufficientFundsException;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User userId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date paymentDate;
    @Column(nullable = false)
    private boolean paymentStatus;

    public Payment(){ }

    public Payment(User userId, double amount,
                   Date paymentDate, boolean paymentStatus) {
        this.userId = userId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // --------------- Getters and Setters (start) ------------------

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    // --------------- Getters and Setters (end) ------------------

}
