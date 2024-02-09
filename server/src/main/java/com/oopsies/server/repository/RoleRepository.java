package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByName(EnumRole name);
}
