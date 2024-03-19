package com.oopsies.server.util;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.services.ImageService;

/**
 * DatabaseDataInjection inserts the necessary database into the DB if they do
 * not exist.
 * This is to ensure consistency within development, since we are not using a
 * cloud-based storage.
 */
@Component
public class DatabaseDataInjection implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Roles Injection
        if (roleRepository.findByName(EnumRole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_USER));
        }
        if (roleRepository.findByName(EnumRole.ROLE_OFFICER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_OFFICER));
        }
        if (roleRepository.findByName(EnumRole.ROLE_MANAGER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_MANAGER));
        }

        // Admin User Injection
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User("admin@gmail.com", encoder.encode("admin"), "Admin", "Admin");
            admin.setRoles(new HashSet<>(Arrays.asList(
                    roleRepository.findByName(EnumRole.ROLE_MANAGER).get(),
                    roleRepository.findByName(EnumRole.ROLE_OFFICER).get())));

            userRepository.save(admin);
        }

        // Regular User Injection
        if (userRepository.findByEmail("user@gmail.com").isEmpty()) {
            User admin = new User("user@gmail.com", encoder.encode("user"), "User", "Teo");
            userRepository.save(admin);
        }

        // Image injections
        // login_background
        // if (imageService.getImageByName("login_background") == null) {
        // imageService.saveImage("login_background", "");
        // }

    }
}