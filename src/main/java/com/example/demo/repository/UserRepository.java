package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their Firebase UID.
     * This is the primary method to check if a user already exists in our database.
     *
     * @param firebaseUid The unique ID provided by Firebase.
     * @return The User entity, or null if not found.
     */
    User findByFirebaseUid(String firebaseUid);
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email.
     * This is used to check for existing users during registration.
     *
     * @param email The user's email.
     * @return The User entity, or null if not found.
     */
    User findByEmail(String email);
}
