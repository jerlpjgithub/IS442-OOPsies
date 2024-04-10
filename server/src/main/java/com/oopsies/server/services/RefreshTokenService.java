package com.oopsies.server.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.RefreshToken;
import com.oopsies.server.exception.TokenRefreshException;
import com.oopsies.server.repository.RefreshTokenRepository;
import com.oopsies.server.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service for handling RefreshToken entities.
 * This service provides methods for creating, retrieving, verifying, and
 * deleting refresh tokens.
 */
@Service
public class RefreshTokenService {
    @Value("${oopsies.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a RefreshToken entity by its token.
     *
     * @param token The token of the RefreshToken entity to retrieve.
     * @return An Optional containing the RefreshToken entity if found, or an empty
     *         Optional if not found.
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Creates a new RefreshToken entity or updates an existing one for a user.
     *
     * @param userId The id of the user for whom to create or update the
     *               RefreshToken entity.
     * @return The created or updated RefreshToken entity.
     */
    public RefreshToken createRefreshToken(Long userId) {

        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByUserId(userId);

        RefreshToken refreshToken;
        if (existingTokenOpt.isPresent()) {
            refreshToken = existingTokenOpt.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setUser(userRepository.findById(userId).get());
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        }

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Verifies the expiration of a RefreshToken entity.
     *
     * @param token The RefreshToken entity to verify.
     * @return The verified RefreshToken entity.
     * @throws TokenRefreshException If the RefreshToken entity is expired.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    /**
     * Deletes a RefreshToken entity by its associated user's id.
     *
     * @param userId The id of the User entity associated with the RefreshToken
     *               entity to delete.
     * @return The number of RefreshToken entities deleted.
     */
    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
