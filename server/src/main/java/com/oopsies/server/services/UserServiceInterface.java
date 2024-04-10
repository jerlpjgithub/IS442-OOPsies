package com.oopsies.server.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oopsies.server.entity.User;

/**
 * Interface for User services.
 * This interface provides methods for finding a user by email, updating a user,
 * finding all users, getting a user by id, and searching for a user by query or
 * id.
 */
public interface UserServiceInterface {

    /**
     * Finds a User entity by its email.
     *
     * @param email The email of the User entity to find.
     * @return The found User entity.
     */
    User findByEmail(String email);

    /**
     * Updates a User entity.
     *
     * @param email The email of the User entity to update.
     * @param user  The new User entity data.
     * @return The updated User entity.
     */
    User updateUser(String email, User user);

    /**
     * Finds all User entities.
     *
     * @param pageable The pagination information.
     * @return A page of User entities.
     */
    Page<User> findAllUsers(Pageable pageable);

    /**
     * Gets a User entity by its id.
     *
     * @param userId The id of the User entity to get.
     * @return The found User entity.
     */
    User getUserById(Long userId);

    /**
     * Searches for a User entity.
     *
     * @param query The query to use for the search.
     * @return The found User entity.
     */
    User searchUser(String query);

    /**
     * Searches for a User entity by its id.
     *
     * @param id The id of the User entity to search for.
     * @return The found User entity.
     */
    User searchUserById(Long id);
}