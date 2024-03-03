package com.oopsies.server.services;
import com.oopsies.server.entity.Refund;
import com.oopsies.server.repository.RefundRepository;

import org.springframework.stereotype.Service;

@Service
public class RefundService {

  private RefundRepository refundRepository;

  public Refund createRefund(Refund refund){
    return refundRepository.save(refund);
  }
}
