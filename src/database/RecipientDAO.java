package database;

import models.Recipient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Recipient operations
 */
public class RecipientDAO {
    
    /**
     * Register a new recipient
     */
    public static boolean registerRecipient(Recipient recipient) {
        String sql = "INSERT INTO recipient (user_id, name, age, gender, blood_group_needed, " +
                     "organ_needed, contact, location, urgency_level, medical_condition) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, recipient.getUserId());
            pstmt.setString(2, recipient.getName());
            pstmt.setInt(3, recipient.getAge());
            pstmt.setString(4, recipient.getGender());
            pstmt.setString(5, recipient.getBloodGroupNeeded());
            pstmt.setString(6, recipient.getOrganNeeded());
            pstmt.setString(7, recipient.getContact());
            pstmt.setString(8, recipient.getLocation());
            pstmt.setString(9, recipient.getUrgencyLevel());
            pstmt.setString(10, recipient.getMedicalCondition());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error registering recipient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get recipient by user ID
     */
    public static Recipient getRecipientByUserId(int userId) {
        String sql = "SELECT * FROM recipient WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractRecipientFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching recipient: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get recipient by recipient ID
     */
    public static Recipient getRecipientById(int recipientId) {
        String sql = "SELECT * FROM recipient WHERE recipient_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, recipientId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractRecipientFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching recipient: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Update recipient information
     */
    public static boolean updateRecipient(Recipient recipient) {
        String sql = "UPDATE recipient SET name = ?, age = ?, gender = ?, " +
                     "blood_group_needed = ?, organ_needed = ?, contact = ?, " +
                     "location = ?, urgency_level = ?, medical_condition = ? " +
                     "WHERE recipient_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, recipient.getName());
            pstmt.setInt(2, recipient.getAge());
            pstmt.setString(3, recipient.getGender());
            pstmt.setString(4, recipient.getBloodGroupNeeded());
            pstmt.setString(5, recipient.getOrganNeeded());
            pstmt.setString(6, recipient.getContact());
            pstmt.setString(7, recipient.getLocation());
            pstmt.setString(8, recipient.getUrgencyLevel());
            pstmt.setString(9, recipient.getMedicalCondition());
            pstmt.setInt(10, recipient.getRecipientId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating recipient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all recipients
     */
    public static List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM recipient ORDER BY registration_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                recipients.add(extractRecipientFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching recipients: " + e.getMessage());
            e.printStackTrace();
        }
        
        return recipients;
    }
    
    /**
     * Helper method to extract Recipient object from ResultSet
     */
    private static Recipient extractRecipientFromResultSet(ResultSet rs) throws SQLException {
        Recipient recipient = new Recipient();
        recipient.setRecipientId(rs.getInt("recipient_id"));
        recipient.setUserId(rs.getInt("user_id"));
        recipient.setName(rs.getString("name"));
        recipient.setAge(rs.getInt("age"));
        recipient.setGender(rs.getString("gender"));
        recipient.setBloodGroupNeeded(rs.getString("blood_group_needed"));
        recipient.setOrganNeeded(rs.getString("organ_needed"));
        recipient.setContact(rs.getString("contact"));
        recipient.setLocation(rs.getString("location"));
        recipient.setUrgencyLevel(rs.getString("urgency_level"));
        recipient.setMedicalCondition(rs.getString("medical_condition"));
        return recipient;
    }
}
