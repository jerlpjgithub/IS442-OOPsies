package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.oopsies.server.entity.RefreshToken;
import com.oopsies.server.entity.User;

/**
 * Repository interface for RefreshToken entities.
 * This interface defines several methods for querying and modifying RefreshToken entities in the database.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    /**
     * Finds a RefreshToken entity by its token.
     *
     * @param token The token of the RefreshToken entity to find.
     * @return An Optional containing the RefreshToken entity if found, or an empty Optional if not found.
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Finds a RefreshToken entity by its associated user's id.
     *
     * @param userId The id of the User entity associated with the RefreshToken entity to find.
     * @return An Optional containing the RefreshToken entity if found, or an empty Optional if not found.
     */
    Optional<RefreshToken> findByUserId(Long userId);
    
    /**
     * Deletes a RefreshToken entity by its associated user.
     *
     * @param user The User entity associated with the RefreshToken entity to delete.
     * @return The number of RefreshToken entities deleted.
     */
    @Modifying
    int deleteByUser(User user);
    
}