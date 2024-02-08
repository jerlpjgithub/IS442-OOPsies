package com.oopsies.server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    @Value("${jwt.secret}")
    private static String SECRET;

    @Value("${jwt.expires.in}")
    private static long EXPIRATION_TIME;

    public static String generateToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
