package com.example.demo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object (DTO) for handling user registration requests.
 * This class is used to receive data (email, password) from the frontend.
 */
@Data
@NoArgsConstructor
public class RegistrationRequest {
	 private String email;
	    private String password;
	    private String firstName;
	    private String lastName;

	    public RegistrationRequest(String email, String password, String firstName, String lastName) {
	        this.email = email;
	        this.password = password;
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }

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
}
