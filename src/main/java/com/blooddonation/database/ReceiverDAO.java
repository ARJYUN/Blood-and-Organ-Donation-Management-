package com.blooddonation.database;

import com.blooddonation.model.Receiver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Receiver operations
 * Handles all database operations related to receivers
 */
public class ReceiverDAO {
    private DatabaseConnection dbConnection;
    
    public ReceiverDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Create a new receiver
     * @param receiver Receiver object to create
     * @return true if successful, false otherwise
     */
    public boolean createReceiver(Receiver receiver) {
        String sql = "INSERT INTO receivers (user_id, required_blood_group, required_organ, urgency_level, status, medical_condition, hospital_id, request_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, receiver.getUserId());
            stmt.setString(2, receiver.getRequiredBloodGroup().name());
            stmt.setString(3, receiver.getRequiredOrgan().name());
            stmt.setString(4, receiver.getUrgencyLevel().name());
            stmt.setString(5, receiver.getStatus().name());
            stmt.setString(6, receiver.getMedicalCondition());
            stmt.setInt(7, receiver.getHospitalId());
            stmt.setDate(8, Date.valueOf(receiver.getRequestDate()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        receiver.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating receiver: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Find receiver by ID
     * @param id Receiver ID
     * @return Receiver object if found, null otherwise
     */
    public Receiver findById(int id) {
        String sql = "SELECT * FROM receivers WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReceiver(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding receiver by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Find receivers by user ID
     * @param userId User ID
     * @return List of receivers for the user
     */
    public List<Receiver> findByUserId(int userId) {
        List<Receiver> receivers = new ArrayList<>();
        String sql = "SELECT * FROM receivers WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    receivers.add(mapResultSetToReceiver(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding receivers by user ID: " + e.getMessage());
        }
        return receivers;
    }
    
    /**
     * Get all receivers
     * @return List of all receivers
     */
    public List<Receiver> getAllReceivers() {
        List<Receiver> receivers = new ArrayList<>();
        String sql = "SELECT * FROM receivers ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                receivers.add(mapResultSetToReceiver(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all receivers: " + e.getMessage());
        }
        return receivers;
    }
    
    /**
     * Find receivers by blood group and organ type
     * @param bloodGroup Blood group to search for
     * @param organType Organ type to search for
     * @return List of matching receivers
     */
    public List<Receiver> findReceiversByRequirements(Receiver.BloodGroup bloodGroup, Receiver.OrganType organType) {
        List<Receiver> receivers = new ArrayList<>();
        String sql = "SELECT * FROM receivers WHERE required_blood_group = ? AND required_organ = ? AND status = 'PENDING' ORDER BY urgency_level DESC, created_at ASC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bloodGroup.name());
            stmt.setString(2, organType.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    receivers.add(mapResultSetToReceiver(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding receivers by requirements: " + e.getMessage());
        }
        return receivers;
    }
    
    /**
     * Update receiver information
     * @param receiver Receiver object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateReceiver(Receiver receiver) {
        String sql = "UPDATE receivers SET required_blood_group = ?, required_organ = ?, urgency_level = ?, status = ?, medical_condition = ?, hospital_id = ?, request_date = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, receiver.getRequiredBloodGroup().name());
            stmt.setString(2, receiver.getRequiredOrgan().name());
            stmt.setString(3, receiver.getUrgencyLevel().name());
            stmt.setString(4, receiver.getStatus().name());
            stmt.setString(5, receiver.getMedicalCondition());
            stmt.setInt(6, receiver.getHospitalId());
            stmt.setDate(7, Date.valueOf(receiver.getRequestDate()));
            stmt.setInt(8, receiver.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating receiver: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update receiver status
     * @param id Receiver ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateReceiverStatus(int id, Receiver.RequestStatus status) {
        String sql = "UPDATE receivers SET status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.name());
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating receiver status: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete receiver by ID
     * @param id Receiver ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteReceiver(int id) {
        String sql = "DELETE FROM receivers WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting receiver: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get receiver statistics
     * @return Array with [total receivers, pending, approved, rejected, fulfilled]
     */
    public int[] getReceiverStatistics() {
        int[] stats = new int[5];
        String sql = "SELECT " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) as pending, " +
                    "SUM(CASE WHEN status = 'APPROVED' THEN 1 ELSE 0 END) as approved, " +
                    "SUM(CASE WHEN status = 'REJECTED' THEN 1 ELSE 0 END) as rejected, " +
                    "SUM(CASE WHEN status = 'FULFILLED' THEN 1 ELSE 0 END) as fulfilled " +
                    "FROM receivers";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("pending");
                stats[2] = rs.getInt("approved");
                stats[3] = rs.getInt("rejected");
                stats[4] = rs.getInt("fulfilled");
            }
        } catch (SQLException e) {
            System.err.println("Error getting receiver statistics: " + e.getMessage());
        }
        return stats;
    }
    
    /**
     * Map ResultSet to Receiver object
     * @param rs ResultSet from database query
     * @return Receiver object
     * @throws SQLException if mapping fails
     */
    private Receiver mapResultSetToReceiver(ResultSet rs) throws SQLException {
        Receiver receiver = new Receiver();
        receiver.setId(rs.getInt("id"));
        receiver.setUserId(rs.getInt("user_id"));
        receiver.setRequiredBloodGroup(Receiver.BloodGroup.valueOf(rs.getString("required_blood_group")));
        receiver.setRequiredOrgan(Receiver.OrganType.valueOf(rs.getString("required_organ")));
        receiver.setUrgencyLevel(Receiver.UrgencyLevel.valueOf(rs.getString("urgency_level")));
        receiver.setStatus(Receiver.RequestStatus.valueOf(rs.getString("status")));
        receiver.setMedicalCondition(rs.getString("medical_condition"));
        receiver.setHospitalId(rs.getInt("hospital_id"));
        
        Date requestDate = rs.getDate("request_date");
        if (requestDate != null) {
            receiver.setRequestDate(requestDate.toLocalDate());
        }
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            receiver.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            receiver.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return receiver;
    }
}
