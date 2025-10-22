package com.blooddonation.database;

import com.blooddonation.model.Donor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Donor operations
 * Handles all database operations related to donors
 */
public class DonorDAO {
    private DatabaseConnection dbConnection;
    
    public DonorDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Create a new donor
     * @param donor Donor object to create
     * @return true if successful, false otherwise
     */
    public boolean createDonor(Donor donor) {
        String sql = "INSERT INTO donors (user_id, blood_group, organ_type, availability_status, last_donation_date, medical_clearance, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, donor.getUserId());
            stmt.setString(2, donor.getBloodGroup().name());
            stmt.setString(3, donor.getOrganType().name());
            stmt.setString(4, donor.getAvailabilityStatus().name());
            stmt.setDate(5, donor.getLastDonationDate() != null ? Date.valueOf(donor.getLastDonationDate()) : null);
            stmt.setBoolean(6, donor.isMedicalClearance());
            stmt.setString(7, donor.getNotes());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        donor.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating donor: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Find donor by ID
     * @param id Donor ID
     * @return Donor object if found, null otherwise
     */
    public Donor findById(int id) {
        String sql = "SELECT * FROM donors WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDonor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding donor by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Find donors by user ID
     * @param userId User ID
     * @return List of donors for the user
     */
    public List<Donor> findByUserId(int userId) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    donors.add(mapResultSetToDonor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding donors by user ID: " + e.getMessage());
        }
        return donors;
    }
    
    /**
     * Get all donors
     * @return List of all donors
     */
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                donors.add(mapResultSetToDonor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all donors: " + e.getMessage());
        }
        return donors;
    }
    
    /**
     * Find available donors by blood group and organ type
     * @param bloodGroup Blood group to search for
     * @param organType Organ type to search for
     * @return List of available donors
     */
    public List<Donor> findAvailableDonors(Donor.BloodGroup bloodGroup, Donor.OrganType organType) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group = ? AND organ_type = ? AND availability_status = 'AVAILABLE' ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bloodGroup.name());
            stmt.setString(2, organType.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    donors.add(mapResultSetToDonor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding available donors: " + e.getMessage());
        }
        return donors;
    }
    
    /**
     * Update donor information
     * @param donor Donor object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateDonor(Donor donor) {
        String sql = "UPDATE donors SET blood_group = ?, organ_type = ?, availability_status = ?, last_donation_date = ?, medical_clearance = ?, notes = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, donor.getBloodGroup().name());
            stmt.setString(2, donor.getOrganType().name());
            stmt.setString(3, donor.getAvailabilityStatus().name());
            stmt.setDate(4, donor.getLastDonationDate() != null ? Date.valueOf(donor.getLastDonationDate()) : null);
            stmt.setBoolean(5, donor.isMedicalClearance());
            stmt.setString(6, donor.getNotes());
            stmt.setInt(7, donor.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating donor: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete donor by ID
     * @param id Donor ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDonor(int id) {
        String sql = "DELETE FROM donors WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting donor: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get donor statistics
     * @return Array with [total donors, available donors, unavailable donors]
     */
    public int[] getDonorStatistics() {
        int[] stats = new int[3];
        String sql = "SELECT " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN availability_status = 'AVAILABLE' THEN 1 ELSE 0 END) as available, " +
                    "SUM(CASE WHEN availability_status = 'UNAVAILABLE' THEN 1 ELSE 0 END) as unavailable " +
                    "FROM donors";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("available");
                stats[2] = rs.getInt("unavailable");
            }
        } catch (SQLException e) {
            System.err.println("Error getting donor statistics: " + e.getMessage());
        }
        return stats;
    }
    
    /**
     * Map ResultSet to Donor object
     * @param rs ResultSet from database query
     * @return Donor object
     * @throws SQLException if mapping fails
     */
    private Donor mapResultSetToDonor(ResultSet rs) throws SQLException {
        Donor donor = new Donor();
        donor.setId(rs.getInt("id"));
        donor.setUserId(rs.getInt("user_id"));
        donor.setBloodGroup(Donor.BloodGroup.valueOf(rs.getString("blood_group")));
        donor.setOrganType(Donor.OrganType.valueOf(rs.getString("organ_type")));
        donor.setAvailabilityStatus(Donor.AvailabilityStatus.valueOf(rs.getString("availability_status")));
        
        Date lastDonationDate = rs.getDate("last_donation_date");
        if (lastDonationDate != null) {
            donor.setLastDonationDate(lastDonationDate.toLocalDate());
        }
        
        donor.setMedicalClearance(rs.getBoolean("medical_clearance"));
        donor.setNotes(rs.getString("notes"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            donor.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            donor.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return donor;
    }
}
