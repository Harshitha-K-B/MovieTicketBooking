package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegistrationRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for handling user-related requests.
 * All endpoints for user management are defined here.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    /**
     * Handles user registration.
     *
     * @param request The registration request containing email and password.
     * @return A ResponseEntity with the created user data or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegistrationRequest request) {
        try {
            User newUser = userService.registerNewUser(request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", newUser.getFirebaseUid());
            response.put("email", newUser.getEmail());
            response.put("firstName", newUser.getFirstName());
            response.put("lastName", newUser.getLastName());
            response.put("isAdmin", newUser.getIsAdmin() != null ? newUser.getIsAdmin() : false);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles user login.
     *
     * @param request The login request containing email and password.
     * @return A ResponseEntity with the logged-in user data or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest request) {
        try {
            User user = userService.loginUser(request.getEmail(), request.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getFirebaseUid());
            response.put("email", user.getEmail());
            response.put("isAdmin", user.getIsAdmin());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {
        // Check if admin already exists (optional)
        if(userRepository.existsByEmail("admin@example.com")) {
            return ResponseEntity.badRequest().body("Admin already exists!");
        }

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123"); // Make sure to hash it if you normally hash passwords
        admin.setIsAdmin(true);

        userRepository.save(admin);
        return ResponseEntity.ok("Admin created successfully!");
    }

}
