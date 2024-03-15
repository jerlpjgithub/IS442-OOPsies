 package com.oopsies.server.entity;

 import jakarta.persistence.*;

 import java.util.Date;

 @Entity
 @Table(name = "payment")
 public class Payment {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int paymentId;

     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "user_id")
     private User user;

     @Column(nullable = false)
     private double amount;

     @Column(nullable = false)
     private Date paymentDate;
//     @Column(nullable = false)
//     private boolean paymentStatus;

     public Payment(){ }

     public Payment(User user, double amount,
                    Date paymentDate) {
         this.user = user;
         this.amount = amount;
         this.paymentDate = paymentDate;
//         this.paymentStatus = paymentStatus;
     }

     // --------------- Getters and Setters (start) ------------------

     public int getPaymentId() {
         return paymentId;
     }

     public void setPaymentId(int paymentId) {
         this.paymentId = paymentId;
     }

     public User getUser() {
         return user;
     }

     public void setUser(User user) {
         this.user = user;
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
                 ", userId=" + user.getId() +
                 ", user name=" + user.getFirstName() + " " + user.getLastName() +
                 ", amount=" + amount +
                 ", paymentDate=" + paymentDate +
//                 ", paymentStatus=" + paymentStatus +
                 '}';
     }
 }
