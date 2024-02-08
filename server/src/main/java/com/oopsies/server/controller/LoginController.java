package com.oopsies.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.util.JwtUtil;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Placeholder for authentication logic
        // You should authenticate your user with the provided credentials here

        // If authentication is successful, generate JWT
        String role = "USER"; // This should be determined based on the authenticated user
        String token = JwtUtil.generateToken(loginRequest.getEmail(), role);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return this.token;
        }
    }
}
