package com.blooddonation.controller;

import com.blooddonation.database.UserDAO;
import com.blooddonation.model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Authentication Controller
 * Handles user authentication, registration, and password management
 */
public class AuthController {
    private UserDAO userDAO;
    
    public AuthController() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Authenticate user login
     * @param email User email
     * @param password User password
     * @return User object if authentication successful, null otherwise
     */
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }
        
        User user = userDAO.findByEmail(email.trim());
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    /**
     * Register a new user
     * @param name User's full name
     * @param email User's email
     * @param password User's password
     * @param role User's role
     * @param location User's location
     * @param phone User's phone number
     * @return User object if registration successful, null otherwise
     */
    public User register(String name, String email, String password, User.UserRole role, String location, String phone) {
        // Validate input
        if (!isValidRegistration(name, email, password, role, location, phone)) {
            return null;
        }
        
        // Check if email already exists
        if (userDAO.emailExists(email.trim())) {
            return null;
        }
        
        // Hash password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // Create user object
        User user = new User(name.trim(), email.trim().toLowerCase(), hashedPassword, role, location.trim(), phone.trim());
        
        // Save to database
        if (userDAO.createUser(user)) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Validate registration input
     * @param name User's name
     * @param email User's email
     * @param password User's password
     * @param role User's role
     * @param location User's location
     * @param phone User's phone
     * @return true if valid, false otherwise
     */
    private boolean isValidRegistration(String name, String email, String password, User.UserRole role, String location, String phone) {
        // Check for null or empty values
        if (name == null || name.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (role == null) return false;
        if (location == null || location.trim().isEmpty()) return false;
        if (phone == null || phone.trim().isEmpty()) return false;
        
        // Validate email format
        if (!isValidEmail(email.trim())) return false;
        
        // Validate password strength
        if (password.length() < 6) return false;
        
        // Validate phone format (basic check)
        if (!isValidPhone(phone.trim())) return false;
        
        return true;
    }
    
    /**
     * Validate email format
     * @param email Email to validate
     * @return true if valid email format, false otherwise
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Validate phone format
     * @param phone Phone to validate
     * @return true if valid phone format, false otherwise
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9\\-\\+\\(\\)\\s]+$") && phone.length() >= 10;
    }
    
    /**
     * Update user password
     * @param userId User ID
     * @param oldPassword Current password
     * @param newPassword New password
     * @return true if successful, false otherwise
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            return false;
        }
        
        User user = userDAO.findById(userId);
        if (user == null || !BCrypt.checkpw(oldPassword, user.getPassword())) {
            return false;
        }
        
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(hashedNewPassword);
        
        return userDAO.updateUser(user);
    }
    
    /**
     * Get user by ID
     * @param userId User ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }
    
    /**
     * Update user profile
     * @param user User object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateProfile(User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty()) {
            return false;
        }
        
        return userDAO.updateUser(user);
    }
    
    /**
     * Check if email exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return userDAO.emailExists(email);
    }
}
