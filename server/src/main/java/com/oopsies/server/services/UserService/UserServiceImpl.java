package com.oopsies.server.services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.User;
import com.oopsies.server.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null.");
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(String email, User user) {
        Optional<User> existingUserOptional = userRepository.findByEmail(email);
        if (!existingUserOptional.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User existingUser = existingUserOptional.get();

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalExistingUser = userRepository.findByEmail(email);

        if (!optionalExistingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User existingUser = optionalExistingUser.get();

        return existingUser;
    }

    // Removed implementation of delete as it requires cascade deletion.
    // @Override
    // public void deleteUserById(Long id) {
    // if (id == null) {
    // throw new IllegalArgumentException("Id must not be null");
    // }
    // userRepository.deleteById(id);
    // }
    @Override
    public User searchUser(String query) {
        Optional<User> optionalUser = userRepository.searchUser(query);

        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();

        return user;
    }

    @Override
    public User searchUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        Optional<User> optionalUser = userRepository.searchUserById(id);

        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }

        User user = optionalUser.get();

        return user;
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }
}
