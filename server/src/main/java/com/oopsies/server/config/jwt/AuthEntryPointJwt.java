package com.oopsies.server.config.jwt;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Component that handles unauthorized access attempts.
 * This class is invoked when a user tries to access a protected resource without proper authentication.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Handles unauthorized access attempts.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param authException The exception that was thrown due to the unauthorized access attempt.
     * @throws IOException If an input or output exception occurred.
     * @throws ServletException If the request could not be handled.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
        throws IOException, ServletException {
            logger.error("Unauthorized error: {}", authException.getMessage(), authException);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        }
}
