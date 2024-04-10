package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;

/**
 * Repository interface for Role entities.
 * This interface defines a method for querying Role entities in the database.
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{

    /**
     * Finds a Role entity by its name.
     *
     * @param name The name of the Role entity to find.
     * @return An Optional containing the Role entity if found, or an empty Optional if not found.
     */
    Optional<Role> findByName(EnumRole name);
}