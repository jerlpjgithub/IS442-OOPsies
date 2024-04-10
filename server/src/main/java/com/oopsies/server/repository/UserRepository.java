package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oopsies.server.entity.User;

/**
 * Repository interface for User entities.
 * This interface defines several methods for querying User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by its email.
     *
     * @param email The email of the User entity to find.
     * @return An Optional containing the User entity if found, or an empty Optional if not found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a User entity exists by its email.
     *
     * @param email The email of the User entity to check.
     * @return A boolean indicating whether a User entity with the given email exists.
     */
    boolean existsByEmail(String email);

    /**
     * Searches for a User entity by its email or first name.
     *
     * @param query The email or first name of the User entity to search for.
     * @return An Optional containing the User entity if found, or an empty Optional if not found.
     */
    @Query("SELECT u FROM User u WHERE u.email = :query OR u.firstName = :query")
    Optional<User> searchUser(@Param("query") String query);

    /**
     * Searches for a User entity by its id.
     *
     * @param id The id of the User entity to search for.
     * @return An Optional containing the User entity if found, or an empty Optional if not found.
     */
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> searchUserById(@Param("id") Long id);
}