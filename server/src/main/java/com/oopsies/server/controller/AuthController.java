package com.oopsies.server.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.dto.GoogleLoginDTO;
import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.GoogleUser;
import com.oopsies.server.entity.RefreshToken;
import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.User;
import com.oopsies.server.exception.TokenRefreshException;
import com.oopsies.server.payload.request.LoginRequest;
import com.oopsies.server.payload.request.SignupRequest;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.payload.response.UserInfoResponse;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.services.GoogleOAuth2Service;
import com.oopsies.server.services.RefreshTokenService;
import com.oopsies.server.services.UserDetailsImpl;
import com.oopsies.server.services.UserDetailsServiceImpl;
import com.oopsies.server.util.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Controller for handling authentication-related requests.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        UserDetailsServiceImpl userDetailsService;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        JwtUtils jwtUtils;

        @Autowired
        RefreshTokenService refreshTokenService;

        @Autowired
        GoogleOAuth2Service googleOAuth2Service;

        /**
         * Endpoint for authenticating a user via Google OAuth.
         *
         * @param googleLoginRequest The Google login request.
         * @return The response entity containing user info or an error message.
         */
        @PostMapping("/google/callback")
        public ResponseEntity<?> googleAuthenticateUser(@RequestBody GoogleLoginDTO googleLoginRequest) {
                try {
                        GoogleUser googleUser = googleOAuth2Service.getUserInfo(googleLoginRequest.getCredential());

                        User user = userRepository.findByEmail(googleUser.getEmail()).orElseGet(() -> {
                                String randomPassword = UUID.randomUUID().toString().replace("-", "");
                                User newUser = new User(googleUser.getEmail(), encoder.encode(randomPassword),
                                                googleUser.getName(), "", 1000.0);

                                // Default Role User is set
                                Set<Role> roles = new HashSet<>();
                                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                                                .orElseThrow(() -> new RuntimeException(
                                                                "Error: User role is not found."));
                                roles.add(userRole);

                                newUser.setRoles(roles);

                                return userRepository.save(newUser);
                        });

                        // Load UserDetails using your custom UserDetailsService
                        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                                        .loadUserByUsername(user.getEmail());

                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

                        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());

                        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
                        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

                        return ResponseEntity.ok()
                                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                                        .body(new UserInfoResponse(userDetails.getId(),
                                                        userDetails.getEmail(),
                                                        roles));
                } catch (GeneralSecurityException | IOException e) {
                        return ResponseEntity
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("An error occurred while processing Google login: " + e.getMessage());
                }
        }

        /**
         * Endpoint for authenticating a user with username and password.
         *
         * @param loginRequest The login request.
         * @return The response entity containing user info or an error message. Access
         *         token and refresh token sent via cookies.
         */
        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

                List<String> roles = userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

                ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                                .body(new MessageResponse<>(
                                                200, "User signed in!", new UserInfoResponse(userDetails.getId(),
                                                                userDetails.getEmail(), roles)));
        }

        /**
         * Endpoint for checking the authentication status of a user.
         *
         * @return The response entity containing user info if authenticated, or an
         *         error message.
         */
        @GetMapping("/status")
        public ResponseEntity<?> checkAuthenticationStatus() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                boolean isAuthenticated = authentication != null
                                && !(authentication instanceof AnonymousAuthenticationToken)
                                && authentication.isAuthenticated();

                if (isAuthenticated) {
                        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(item -> item.getAuthority())
                                        .collect(Collectors.toList());

                        return ResponseEntity.ok(new MessageResponse<>(
                                        200, "successful",
                                        new UserInfoResponse(userDetails.getId(), userDetails.getEmail(), roles)));
                } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new MessageResponse<>(
                                                        403, "User is not authenticated", null));
                }
        }

        /**
         * Endpoint for registering a new user.
         *
         * @param signupRequest The signup request.
         * @return The response entity containing a success message or an error message.
         */
        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
                if (userRepository.existsByEmail(signupRequest.getEmail())) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(new MessageResponse<>(
                                                        400, "Error: Email is already taken!", null));
                }

                // Create user account
                User user = new User(
                                signupRequest.getEmail(),
                                encoder.encode(signupRequest.getPassword()),
                                signupRequest.getFirstName(),
                                signupRequest.getLastName(),
                                1000.0);

                // Default Role User is set
                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(
                                                "Error: User role is not found."));
                roles.add(userRole);

                user.setRoles(roles);
                userRepository.save(user);

                return ResponseEntity.ok(new MessageResponse<>(
                                200, "User registered successfully!", null));
        }

        /**
         * Endpoint for logging out a user.
         *
         * @return The response entity containing a success message.
         */
        @PostMapping("/signout")
        public ResponseEntity<?> logoutUser() {
                Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principle.toString().equals("anonymousUser")) {
                        Long userId = ((UserDetailsImpl) principle).getId();
                        refreshTokenService.deleteByUserId(userId);
                }

                ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
                ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                                .body(new MessageResponse<>(
                                                200, "You've been signed out!", null));
        }

        /**
         * Endpoint for refreshing a JWT token.
         *
         * @param request The HTTP request.
         * @return The response entity containing a new JWT token or an error message.
         */
        @PostMapping("/refreshtoken")
        public ResponseEntity<?> refreshToken(HttpServletRequest request) {
                String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

                if ((refreshToken != null) && (!refreshToken.isEmpty())) {
                        return refreshTokenService.findByToken(refreshToken)
                                        .map(refreshTokenService::verifyExpiration)
                                        .map(RefreshToken::getUser)
                                        .map(user -> {
                                                ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

                                                return ResponseEntity.ok()
                                                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                                                .body(new MessageResponse<>(
                                                                                200, "Token is successfully refreshed!",
                                                                                null));
                                        })
                                        .orElseThrow(() -> new TokenRefreshException(refreshToken,
                                                        "Refresh token is not in database!"));
                }

                return ResponseEntity.badRequest().body(new MessageResponse<>(
                                200, "Refresh Token is empty!", null));
        }
}
