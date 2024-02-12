package com.oopsies.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.services.ImageService;

/**
     * DatabaseDataInjection inserts the necessary database into the DB if they do not exist.
     * This is to ensure consistency within development, since we are not using a cloud-based storage.
 */
@Component
public class DatabaseDataInjection implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageService imageService;

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

        // Image injections
        // login_background
        // if (imageService.getImageByName("login_background") == null) {
        //     imageService.saveImage("login_background", "");
        // }

    }
}