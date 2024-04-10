package com.oopsies.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsies.server.entity.User;
import com.oopsies.server.repository.UserRepository;

/**
 * Service for handling User entities.
 * This service provides methods for loading users by username, retrieving users
 * by id, saving users, and checking user roles.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * Loads a User entity by its username.
     *
     * @param email The email of the User entity to load.
     * @return The loaded UserDetails instance.
     * @throws UsernameNotFoundException If the User entity is not found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return UserDetailsImpl.build(user);
    }

    /**
     * Retrieves a User entity by its id.
     *
     * @param id The id of the User entity to retrieve.
     * @return An Optional containing the User entity if found, or an empty Optional
     *         if not found.
     */
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    /**
     * Saves a User entity.
     *
     * @param user The User entity to save.
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Checks if the currently authenticated user has the manager role.
     *
     * @return true if the user has the manager role, false otherwise.
     */
    public boolean isManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority()
                        .equals("ROLE_MANAGER"));
    }

    /**
     * Checks if the currently authenticated user has the officer role.
     *
     * @return true if the user has the officer role, false otherwise.
     */
    public boolean isOfficer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority()
                        .equals("ROLE_OFFICER"));
    }

    /**
     * Checks if a User entity is the currently authenticated user.
     *
     * @param user The User entity to check.
     * @return true if the User entity is the currently authenticated user, false
     *         otherwise.
     */
    public boolean isAuthorisedUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return ((UserDetailsImpl) authentication.getPrincipal()).getId().equals(user.getId());
        }
        return false;
    }
}
