package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsies.server.entity.Image;

/**
 * Repository interface for Image entities.
 * This interface defines a method for querying Image entities in the database.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Finds an Image entity by its name.
     *
     * @param name The name of the Image entity to find.
     * @return An Optional containing the Image entity if found, or an empty Optional if not found.
     */
    Optional<Image> findByName(String name);
}