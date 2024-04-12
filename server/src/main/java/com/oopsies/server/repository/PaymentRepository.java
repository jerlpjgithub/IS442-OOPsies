package com.oopsies.server.repository;

import com.oopsies.server.entity.Payment;
import com.oopsies.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Payment entities.
 * This interface defines several methods for querying payment entities in the database.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
   /**
    * Finds Payment entity by Payment id
    *
    * @param paymentId The id of the Payment entity we are finding
    * @return Payment entity being found
    */
   Payment findPaymentByPaymentId(long paymentId);
   /**
    * Finds Payment entity by booking id
    *
    * @param bookingId The id of the Payment entity we are finding
    * @return Payment entity being found
    */
   Payment findByBookingId(long bookingId);
}

