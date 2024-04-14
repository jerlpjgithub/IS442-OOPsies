package com.oopsies.server.util;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.oopsies.server.entity.User;
import com.oopsies.server.exception.JwtTokenExpiredException;
import com.oopsies.server.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for handling JWT tokens.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${oopsies.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oopsies.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${oopsies.app.jwtCookieName}")
    private String jwtCookie;

    @Value("${oopsies.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    /**
     * Generates a JWT cookie for a given user principal.
     *
     * @param userPrincipal the user principal
     * @return the JWT cookie
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromEmail(userPrincipal.getEmail());
        return generateCookie(jwtCookie, jwt, "/api");
    }

    /**
     * Generates a JWT cookie for a given user.
     *
     * @param user the user
     * @return the JWT cookie
     */
    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateTokenFromEmail(user.getEmail());
        return generateCookie(jwtCookie, jwt, "/api");
    }

    /**
     * Generates a refresh JWT cookie for a given refresh token.
     *
     * @param refreshToken the refresh token
     * @return the refresh JWT cookie
     */
    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken");
    }

    /**
     * Retrieves the JWT from the cookies of a given request.
     *
     * @param request the HTTP request
     * @return the JWT
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }

    /**
     * Retrieves the refresh JWT from the cookies of a given request.
     *
     * @param request the HTTP request
     * @return the refresh JWT
     */
    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    /**
     * Generates a clean JWT cookie.
     *
     * @return the clean JWT cookie
     */
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    /**
     * Generates a clean refresh JWT cookie.
     *
     * @return the clean refresh JWT cookie
     */
    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();
        return cookie;
    }

    /**
     * Retrieves the email from a given JWT token.
     *
     * @param token the JWT token
     * @return the email
     */
    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Generates a key for signing the JWT.
     *
     * @return the key
     */
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validates a given JWT token.
     *
     * @param authToken the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtTokenExpiredException("JWT token expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Generates a JWT token from a given email.
     *
     * @param email the email
     * @return the JWT token
     */
    public String generateTokenFromEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a cookie with a given name, value, and path.
     *
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path of the cookie
     * @return the cookie
     */
    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(48 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    /**
     * Retrieves the value of a cookie with a given name from a given request.
     *
     * @param request the HTTP request
     * @param name the name of the cookie
     * @return the value of the cookie, or null if the cookie is not found
     */
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

}
