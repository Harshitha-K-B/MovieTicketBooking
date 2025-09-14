package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a user entity in the database.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firebaseUid;
    private String firstName;
    private String lastName;
    private String password; // Added for authentication
    private Boolean isAdmin; // Changed to Boolean to allow null values during migration

    public User() {
    }

    /**
     * Constructor for creating a new user during registration.
     *
     * @param firebaseUid The unique ID from Firebase.
     * @param email The user's email.
     * @param isAdmin A boolean indicating if the user has admin privileges.
     */
    public User(String firebaseUid, String email, Boolean isAdmin) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Existing constructor
    public User(String email, String firebaseUid) {
        this.email = email;
        this.firebaseUid = firebaseUid;
    }

    // Existing constructor
    public User(String email, String firebaseUid, String firstName, String lastName) {
        this.email = email;
        this.firebaseUid = firebaseUid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
