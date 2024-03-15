 package com.oopsies.server.repository;

 import com.oopsies.server.entity.Payment;
 import com.oopsies.server.entity.User;
 import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.List;

 public interface PaymentRepository extends JpaRepository<Payment, Long> {
     Payment findPaymentByPaymentId(int paymentId);
     List<Payment> findByUserId(long userId);
 }

