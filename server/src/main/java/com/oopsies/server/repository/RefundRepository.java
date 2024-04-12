package com.oopsies.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopsies.server.entity.Refund;

/**
 * Repository interface for Refund entities.
 * This interface defines several methods for querying refund entities in the database.
 */
@Repository
public interface RefundRepository extends JpaRepository<Refund, Long>{ }
