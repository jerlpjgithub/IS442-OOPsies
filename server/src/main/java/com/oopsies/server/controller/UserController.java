package com.oopsies.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.dto.UserResponseDTO;
import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.services.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_OFFICER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);

        // Check if a search query is provided
        if (query != null && !query.trim().isEmpty()) {
            try {
                logger.info("Searching for user with query: {}", query);
                User user = null;
                try {
                    // Try to parse the query as a long in case it's an ID
                    Long id = Long.parseLong(query);
                    user = userServiceImpl.searchUserById(id);
                } catch (NumberFormatException e) {
                    // If not a number, search by the query string
                    user = userServiceImpl.searchUser(query);
                }

                if (user == null) {
                    logger.info("User not found with query: {}", query);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                Page<UserResponseDTO> singleUserPage = new PageImpl<>(List.of(convertToDto(user)), pageable, 1);
                return ResponseEntity.ok(Map.of("content", singleUserPage.getContent()));
            } catch (Exception e) {
                logger.error("An error occurred while searching for user with query: {}", query, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // If no query, fetch all users
            Page<User> users = userServiceImpl.findAllUsers(pageable);
            Page<UserResponseDTO> dtoPage = users.map(this::convertToDto);
            return ResponseEntity.ok(dtoPage);
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER', 'ROLE_MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        try {
            User user = userServiceImpl.getUserById(userId);
            return ResponseEntity.ok(convertToDto(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId,
            @RequestBody Map<String, Object> updates,
            Authentication authentication) {
        try {
            User existingUser = userServiceImpl.getUserById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Check roles
            boolean isManager = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));

            // Extract roles if present and convert to Set<Role>
            if (updates.containsKey("roles") && isManager) {
                List<String> roleNames = (List<String>) updates.get("roles");
                Set<Role> roles = new HashSet<>();
                for (String roleName : roleNames) {
                    Optional<Role> roleOptional = roleRepository.findByName(EnumRole.valueOf(roleName));
                    if (roleOptional.isPresent()) {
                        roles.add(roleOptional.get());
                    }
                }
                existingUser.setRoles(roles); // Only update roles if present in the request
            }

            // Directly update firstName and lastName
            if (updates.containsKey("firstName")) {
                existingUser.setFirstName((String) updates.get("firstName"));
            }
            if (updates.containsKey("lastName")) {
                existingUser.setLastName((String) updates.get("lastName"));
            }

            // Save the updated user
            User updatedUser = userServiceImpl.updateUser(existingUser.getEmail(), existingUser);
            return ResponseEntity.ok(convertToDto(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Utility Method to convert User entity to UserResponseDTO
    private UserResponseDTO convertToDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles(),
                user.getEmailVerified(),
                user.getAccountBalance(),
                user.getProvider());
    }
}
