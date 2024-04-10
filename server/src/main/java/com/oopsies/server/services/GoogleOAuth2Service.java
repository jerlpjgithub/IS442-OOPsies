package com.oopsies.server.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.oopsies.server.entity.GoogleUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Service for handling Google OAuth2 operations.
 * This service provides a method for extracting user information from a Google ID token.
 */
@Service
public class GoogleOAuth2Service {

    @Value("${oopsies.oauth.clientid}")
    private String clientId;

    /**
     * Extracts user information from a Google ID token.
     *
     * @param idTokenString The Google ID token as a string.
     * @return A GoogleUser object containing the user's information.
     * @throws GeneralSecurityException If a security exception occurs while verifying the ID token.
     * @throws IOException If an I/O exception occurs while verifying the ID token.
     */
    public GoogleUser getUserInfo(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Extract user information from the payload
            GoogleUser googleUser = new GoogleUser();
            googleUser.setId(payload.getSubject()); // Use Google's subject (sub) as the user ID
            googleUser.setEmail(payload.getEmail());
            googleUser.setName((String) payload.get("name"));

            return googleUser;
        } else {
            throw new IllegalArgumentException("ID Token is invalid");
        }
    }
}
