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

@Service
public class GoogleOAuth2Service {

    @Value("${oopsies.oauth.clientid}")
    private String clientId;

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
