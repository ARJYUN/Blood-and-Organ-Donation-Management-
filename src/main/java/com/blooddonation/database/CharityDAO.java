package com.blooddonation.database;

import com.blooddonation.model.Charity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Charity operations
 * Handles all database operations related to charities
 */
public class CharityDAO {
    private DatabaseConnection dbConnection;
    
    public CharityDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Create a new charity event
     * @param charity Charity object to create
     * @return true if successful, false otherwise
     */
    public boolean createCharity(Charity charity) {
        String sql = "INSERT INTO charities (user_id, organization_name, event_name, event_date, event_location, contact_info, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, charity.getUserId());
            stmt.setString(2, charity.getOrganizationName());
            stmt.setString(3, charity.getEventName());
            stmt.setDate(4, Date.valueOf(charity.getEventDate()));
            stmt.setString(5, charity.getEventLocation());
            stmt.setString(6, charity.getContactInfo());
            stmt.setString(7, charity.getDescription());
            stmt.setString(8, charity.getStatus().name());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        charity.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating charity: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Find charity by ID
     * @param id Charity ID
     * @return Charity object if found, null otherwise
     */
    public Charity findById(int id) {
        String sql = "SELECT * FROM charities WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCharity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding charity by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Find charities by user ID
     * @param userId User ID
     * @return List of charities for the user
     */
    public List<Charity> findByUserId(int userId) {
        List<Charity> charities = new ArrayList<>();
        String sql = "SELECT * FROM charities WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    charities.add(mapResultSetToCharity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding charities by user ID: " + e.getMessage());
        }
        return charities;
    }
    
    /**
     * Get all charities
     * @return List of all charities
     */
    public List<Charity> getAllCharities() {
        List<Charity> charities = new ArrayList<>();
        String sql = "SELECT * FROM charities ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                charities.add(mapResultSetToCharity(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all charities: " + e.getMessage());
        }
        return charities;
    }
    
    /**
     * Update charity information
     * @param charity Charity object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateCharity(Charity charity) {
        String sql = "UPDATE charities SET organization_name = ?, event_name = ?, event_date = ?, event_location = ?, contact_info = ?, description = ?, status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, charity.getOrganizationName());
            stmt.setString(2, charity.getEventName());
            stmt.setDate(3, Date.valueOf(charity.getEventDate()));
            stmt.setString(4, charity.getEventLocation());
            stmt.setString(5, charity.getContactInfo());
            stmt.setString(6, charity.getDescription());
            stmt.setString(7, charity.getStatus().name());
            stmt.setInt(8, charity.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating charity: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update charity status
     * @param id Charity ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateCharityStatus(int id, Charity.EventStatus status) {
        String sql = "UPDATE charities SET status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.name());
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating charity status: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete charity by ID
     * @param id Charity ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteCharity(int id) {
        String sql = "DELETE FROM charities WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting charity: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get charity statistics
     * @return Array with [total charities, active, completed, cancelled]
     */
    public int[] getCharityStatistics() {
        int[] stats = new int[4];
        String sql = "SELECT " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN status = 'ACTIVE' THEN 1 ELSE 0 END) as active, " +
                    "SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) as completed, " +
                    "SUM(CASE WHEN status = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled " +
                    "FROM charities";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("active");
                stats[2] = rs.getInt("completed");
                stats[3] = rs.getInt("cancelled");
            }
        } catch (SQLException e) {
            System.err.println("Error getting charity statistics: " + e.getMessage());
        }
        return stats;
    }
    
    /**
     * Map ResultSet to Charity object
     * @param rs ResultSet from database query
     * @return Charity object
     * @throws SQLException if mapping fails
     */
    private Charity mapResultSetToCharity(ResultSet rs) throws SQLException {
        Charity charity = new Charity();
        charity.setId(rs.getInt("id"));
        charity.setUserId(rs.getInt("user_id"));
        charity.setOrganizationName(rs.getString("organization_name"));
        charity.setEventName(rs.getString("event_name"));
        
        Date eventDate = rs.getDate("event_date");
        if (eventDate != null) {
            charity.setEventDate(eventDate.toLocalDate());
        }
        
        charity.setEventLocation(rs.getString("event_location"));
        charity.setContactInfo(rs.getString("contact_info"));
        charity.setDescription(rs.getString("description"));
        charity.setStatus(Charity.EventStatus.valueOf(rs.getString("status")));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            charity.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            charity.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return charity;
    }
}
