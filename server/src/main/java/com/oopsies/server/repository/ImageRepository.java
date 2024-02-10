package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsies.server.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
