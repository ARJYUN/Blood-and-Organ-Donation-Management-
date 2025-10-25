package database;

import models.User;
import models.Donor;
import models.Receptionist;

import java.sql.*;

/**
 * Data Access Object for User operations
 * Handles CRUD operations for user authentication
 */
public class UserDAO {
    
    /**
     * Authenticate user login
     * @param username Username
     * @param password Password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                
                // Return appropriate user object based on role
                if ("DONOR".equals(role)) {
                    Donor donor = new Donor(username, password);
                    donor.setUserId(userId);
                    return donor;
                } else if ("RECEPTIONIST".equals(role)) {
                    Receptionist receptionist = new Receptionist(username, password);
                    receptionist.setUserId(userId);
                    return receptionist;
                } else {
                    return new User(userId, username, password, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Register a new user
     * @param user User object to register
     * @return Generated user ID if successful, -1 otherwise
     */
    public int registerUser(User user) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Check if username already exists
     * @param username Username to check
     * @return true if exists, false otherwise
     */
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Update user password
     * @param userId User ID
     * @param newPassword New password
     * @return true if successful, false otherwise
     */
    public boolean updatePassword(int userId, String newPassword) {
        String query = "UPDATE user SET password = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
