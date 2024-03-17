package com.oopsies.server.services;
import com.oopsies.server.entity.Booking;
import com.oopsies.server.entity.Refund;
import com.oopsies.server.entity.User;

import com.oopsies.server.repository.RefundRepository;
import com.oopsies.server.repository.BookingRepository;
import com.oopsies.server.repository.UserRepository;

import java.util.Optional;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class RefundService {

  private RefundRepository refundRepository;

  private BookingRepository bookingRepository;

  private UserRepository userRepository;

  public RefundService(RefundRepository refundRepository, BookingRepository bookingRepository, UserRepository userRepository) { 
    this.refundRepository = refundRepository;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
  }
  // public Refund createRefund(Refund refund){
  //   return refundRepository.save(refund);
  // }

  public void processRefund(Long booking_id) throws Exception{

    // Check if refund has already been requested
    Refund refund = refundRepository.findRefundByBookingId(booking_id);
    if(refund != null){
      throw new Exception("refund for this booking has already been processed");
    }

    Booking booking = bookingRepository.findBookingById(booking_id);

    Refund processedRefund = new Refund(booking_id, booking, new Date());
    long user_id = booking.getUser().getId();

    // To change once relation is correct 
    double refundedAmount =  100.00;
    
    // Retrieves current user that booked, and refund amount back to them. 
    Optional<User> user = userRepository.findById(user_id);
    double accountBalance = user.get().getAccountBalance();
    double updatedAccountBalance = accountBalance + refundedAmount;
    user.get().setAccountBalance(updatedAccountBalance);
  
    userRepository.save(user.get());
    
    refundRepository.save(processedRefund);
  }


}
