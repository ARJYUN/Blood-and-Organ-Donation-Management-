package com.donation.controller;

import com.donation.dao.UserDAO;
import com.donation.model.User;

import java.util.logging.Logger;

/**
 * Controller class for authentication operations
 * Handles login, logout, and session management
 */
public class AuthController {
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());
    private static User currentUser = null;
    private final UserDAO userDAO;
    
    public AuthController() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Attempt to login with the provided credentials
     * @param username The username
     * @param password The password
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            currentUser = user;
            LOGGER.info("User logged in: " + username);
            return true;
        }
        LOGGER.warning("Failed login attempt for username: " + username);
        return false;
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        if (currentUser != null) {
            LOGGER.info("User logged out: " + currentUser.getUsername());
            currentUser = null;
        }
    }
    
    /**
     * Check if a user is currently logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Get the currently logged in user
     * @return The current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Check if the current user has admin privileges
     * @return true if the current user is an admin, false otherwise
     */
    public boolean isAdmin() {
        return isLoggedIn() && "ADMIN".equals(currentUser.getRole());
    }
    
    /**
     * Register a new user
     * @param username The username
     * @param password The password
     * @param email The email
     * @param role The role
     * @return The created user, or null if registration failed
     */
    public User registerUser(String username, String password, String email, String role) {
        // Check if username already exists
        if (userDAO.getUserByUsername(username) != null) {
            LOGGER.warning("Registration failed: Username already exists: " + username);
            return null;
        }
        
        User newUser = new User(username, password, email, role);
        User createdUser = userDAO.createUser(newUser);
        
        if (createdUser != null) {
            LOGGER.info("User registered successfully: " + username);
        } else {
            LOGGER.warning("Failed to register user: " + username);
        }
        
        return createdUser;
    }
}