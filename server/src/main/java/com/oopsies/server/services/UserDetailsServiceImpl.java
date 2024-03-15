package com.oopsies.server.services;

import com.oopsies.server.exception.UserInsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsies.server.entity.User;
import com.oopsies.server.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return UserDetailsImpl.build(user);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
