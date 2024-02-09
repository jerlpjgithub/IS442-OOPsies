package com.oopsies.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.oopsies.server.entity.Role;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.entity.EnumRole;

/**
     * RoleInitializer inserts the necessary ROLES into the roles table on run if these roles do not exist.
 */
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(EnumRole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_USER));
        }
        if (roleRepository.findByName(EnumRole.ROLE_OFFICER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_OFFICER));
        }
        if (roleRepository.findByName(EnumRole.ROLE_MANAGER).isEmpty()) {
            roleRepository.save(new Role(EnumRole.ROLE_MANAGER));
        }
    }
}