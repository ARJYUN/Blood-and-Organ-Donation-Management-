package com.blooddonation.database;

import com.blooddonation.model.Hospital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Hospital operations
 * Handles all database operations related to hospitals
 */
public class HospitalDAO {
    private DatabaseConnection dbConnection;
    
    public HospitalDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Create a new hospital
     * @param hospital Hospital object to create
     * @return true if successful, false otherwise
     */
    public boolean createHospital(Hospital hospital) {
        String sql = "INSERT INTO hospitals (name, address, contact_number, email, specialization) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, hospital.getName());
            stmt.setString(2, hospital.getAddress());
            stmt.setString(3, hospital.getContactNumber());
            stmt.setString(4, hospital.getEmail());
            stmt.setString(5, hospital.getSpecialization());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hospital.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating hospital: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Find hospital by ID
     * @param id Hospital ID
     * @return Hospital object if found, null otherwise
     */
    public Hospital findById(int id) {
        String sql = "SELECT * FROM hospitals WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHospital(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding hospital by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all hospitals
     * @return List of all hospitals
     */
    public List<Hospital> getAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "SELECT * FROM hospitals ORDER BY name ASC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                hospitals.add(mapResultSetToHospital(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all hospitals: " + e.getMessage());
        }
        return hospitals;
    }
    
    /**
     * Search hospitals by name
     * @param name Hospital name to search for
     * @return List of matching hospitals
     */
    public List<Hospital> searchHospitalsByName(String name) {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "SELECT * FROM hospitals WHERE name LIKE ? ORDER BY name ASC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hospitals.add(mapResultSetToHospital(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching hospitals by name: " + e.getMessage());
        }
        return hospitals;
    }
    
    /**
     * Update hospital information
     * @param hospital Hospital object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateHospital(Hospital hospital) {
        String sql = "UPDATE hospitals SET name = ?, address = ?, contact_number = ?, email = ?, specialization = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, hospital.getName());
            stmt.setString(2, hospital.getAddress());
            stmt.setString(3, hospital.getContactNumber());
            stmt.setString(4, hospital.getEmail());
            stmt.setString(5, hospital.getSpecialization());
            stmt.setInt(6, hospital.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating hospital: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete hospital by ID
     * @param id Hospital ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteHospital(int id) {
        String sql = "DELETE FROM hospitals WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting hospital: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get hospital statistics
     * @return Total number of hospitals
     */
    public int getHospitalCount() {
        String sql = "SELECT COUNT(*) FROM hospitals";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting hospital count: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Map ResultSet to Hospital object
     * @param rs ResultSet from database query
     * @return Hospital object
     * @throws SQLException if mapping fails
     */
    private Hospital mapResultSetToHospital(ResultSet rs) throws SQLException {
        Hospital hospital = new Hospital();
        hospital.setId(rs.getInt("id"));
        hospital.setName(rs.getString("name"));
        hospital.setAddress(rs.getString("address"));
        hospital.setContactNumber(rs.getString("contact_number"));
        hospital.setEmail(rs.getString("email"));
        hospital.setSpecialization(rs.getString("specialization"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            hospital.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        return hospital;
    }
}
