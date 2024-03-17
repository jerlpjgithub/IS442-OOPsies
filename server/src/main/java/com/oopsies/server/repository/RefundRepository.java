package com.oopsies.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopsies.server.entity.Refund;
@Repository
public interface RefundRepository extends JpaRepository<Refund, Long>{
  Refund findRefundByBookingId(Long id);
}
