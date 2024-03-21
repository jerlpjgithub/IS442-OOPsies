package com.oopsies.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oopsies.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :query OR u.firstName = :query")
    Optional<User> searchUser(@Param("query") String query);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> searchUserById(@Param("id") Long id);
}
