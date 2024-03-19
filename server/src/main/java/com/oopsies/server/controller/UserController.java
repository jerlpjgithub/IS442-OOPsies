package com.oopsies.server.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.services.UserService.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_OFFICER') or hasRole('ROLE_MANAGER')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userServiceImpl.findAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER', 'ROLE_MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userServiceImpl.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_OFFICER', 'ROLE_MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody Map<String, Object> updates,
            Authentication authentication) {
        try {
            User existingUser = userServiceImpl.getUserById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Check roles
            boolean isOfficerOrManager = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OFFICER") ||
                            grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
            
            // Extract roles if present and convert to Set<Role>
            if (updates.containsKey("roles") && isOfficerOrManager) {
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
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // @DeleteMapping("/{userId}")
    // @PreAuthorize("hasAnyRole('ROLE_OFFICER', 'ROLE_MANAGER')")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    //     try {
    //         userServiceImpl.deleteUserById(userId);
    //         return ResponseEntity.noContent().build();
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //     }
    // }

}
