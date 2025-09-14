package com.example.demo.request;

/**
 * Data Transfer Object for user login requests.
 * It encapsulates the data received from the client for a login attempt.
 */
public class LoginRequest {

    private String email;
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
