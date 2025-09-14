package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for managing user-related business logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Registers a new user with a unique ID and email.
     *
     * @param request The registration request containing the user's email.
     * @return The newly created User entity.
     * @throws IllegalStateException if a user with the same email already exists.
     */
    public User registerNewUser(RegistrationRequest request) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if (existingUser.isPresent()) {
            throw new IllegalStateException("User with this email already exists");
        }

        // Generate a random UID as a placeholder for Firebase UID in this example
        String firebaseUid = UUID.randomUUID().toString();
        // Assuming the User constructor now also takes a boolean for isAdmin,
        // we default all new registrations to not be an admin.
        User newUser = new User(firebaseUid, request.getEmail(), false);
        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user based on their email and password.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return The authenticated User entity.
     * @throws IllegalStateException if the authentication fails.
     */
    public User loginUser(String email, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("Invalid email or password");
        }

        User user = userOptional.get();

        // TODO: Implement actual password validation logic here.
        // This will require a password field in your User entity and
        // a hashing mechanism (e.g., Spring Security's BCryptPasswordEncoder).
        // For now, we'll assume the password is correct if the user exists.

        return user;
    }
}
