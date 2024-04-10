package com.oopsies.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.User;
import com.oopsies.server.repository.UserRepository;

/**
 * Service for handling User entities.
 * This service provides methods for finding all users, updating a user, finding
 * a user by email, searching for a user, searching for a user by id, and
 * getting a user by id.
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    /**
     * Finds all User entities.
     *
     * @param pageable The pagination information.
     * @return A page of User entities.
     * @throws IllegalArgumentException If pageable is null.
     */
    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null.");
        }
        return userRepository.findAll(pageable);
    }

    /**
     * Updates a User entity.
     *
     * @param email The email of the User entity to update.
     * @param user  The new User entity data.
     * @return The updated User entity.
     * @throws IllegalArgumentException If the User entity does not exist.
     */
    @Override
    public User updateUser(String email, User user) {
        Optional<User> existingUserOptional = userRepository.findByEmail(email);
        if (!existingUserOptional.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User existingUser = existingUserOptional.get();

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    /**
     * Finds a User entity by its email.
     *
     * @param email The email of the User entity to find.
     * @return The found User entity.
     * @throws IllegalArgumentException If the User entity does not exist.
     */
    @Override
    public User findByEmail(String email) {
        Optional<User> optionalExistingUser = userRepository.findByEmail(email);

        if (!optionalExistingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User existingUser = optionalExistingUser.get();

        return existingUser;
    }

    // Removed implementation of delete as it requires cascade deletion.
    // @Override
    // public void deleteUserById(Long id) {
    // if (id == null) {
    // throw new IllegalArgumentException("Id must not be null");
    // }
    // userRepository.deleteById(id);
    // }

    /**
     * Searches for a User entity.
     *
     * @param query The query to use for the search.
     * @return The found User entity.
     * @throws IllegalArgumentException If the User entity does not exist.
     */
    @Override
    public User searchUser(String query) {
        Optional<User> optionalUser = userRepository.searchUser(query);

        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();

        return user;
    }

    /**
     * Searches for a User entity by its id.
     *
     * @param id The id of the User entity to search for.
     * @return The found User entity.
     * @throws IllegalArgumentException If the User entity does not exist or if id
     *                                  is null.
     */
    @Override
    public User searchUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        Optional<User> optionalUser = userRepository.searchUserById(id);

        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();

        return user;
    }

    /**
     * Gets a User entity by its id.
     *
     * @param id The id of the User entity to get.
     * @return The found User entity.
     * @throws IllegalArgumentException If the User entity does not exist or if id
     *                                  is null.
     */
    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }
}
