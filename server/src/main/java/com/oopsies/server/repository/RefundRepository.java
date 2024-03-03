package com.oopsies.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oopsies.server.entity.Refund;

@Repository
public interface RefundRepository extends CrudRepository<Refund, Long>{
  
}
