package com.oopsies.server.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oopsies.server.entity.User;

public interface UserServiceInterface {
    User findByEmail(String email);
    User updateUser(String email, User user);
    Page<User> findAllUsers(Pageable pageable);
    // void deleteUserById(Long id);
    User getUserById(Long userId);
    User searchUser(String query);
    User searchUserById(Long id);
}
