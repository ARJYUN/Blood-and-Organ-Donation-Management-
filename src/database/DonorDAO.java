package database;

import models.Donor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Donor operations
 * Handles CRUD operations for donor management
 */
public class DonorDAO {
    
    /**
     * Register a new donor
     * @param donor Donor object
     * @param userId Associated user ID
     * @return true if successful, false otherwise
     */
    public boolean registerDonor(Donor donor, int userId) {
        String query = "INSERT INTO donor (user_id, name, age, gender, blood_group, organ, contact, location) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, donor.getName());
            pstmt.setInt(3, donor.getAge());
            pstmt.setString(4, donor.getGender());
            pstmt.setString(5, donor.getBloodGroup());
            pstmt.setString(6, donor.getOrgan());
            pstmt.setString(7, donor.getContact());
            pstmt.setString(8, donor.getLocation());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error registering donor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Get donor by user ID
     * @param userId User ID
     * @return Donor object if found, null otherwise
     */
    public Donor getDonorByUserId(int userId) {
        String query = "SELECT * FROM donor WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractDonorFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching donor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get all donors
     * @return List of all donors
     */
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String query = "SELECT * FROM donor ORDER BY registration_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                donors.add(extractDonorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all donors: " + e.getMessage());
            e.printStackTrace();
        }
        
        return donors;
    }
    
    /**
     * Search donors by blood group
     * @param bloodGroup Blood group to search
     * @return List of matching donors
     */
    public List<Donor> searchByBloodGroup(String bloodGroup) {
        List<Donor> donors = new ArrayList<>();
        String query = "SELECT * FROM donor WHERE blood_group = ? ORDER BY registration_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, bloodGroup);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                donors.add(extractDonorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching donors by blood group: " + e.getMessage());
            e.printStackTrace();
        }
        
        return donors;
    }
    
    /**
     * Search donors by organ
     * @param organ Organ to search
     * @return List of matching donors
     */
    public List<Donor> searchByOrgan(String organ) {
        List<Donor> donors = new ArrayList<>();
        String query = "SELECT * FROM donor WHERE organ LIKE ? ORDER BY registration_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + organ + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                donors.add(extractDonorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching donors by organ: " + e.getMessage());
            e.printStackTrace();
        }
        
        return donors;
    }
    
    /**
     * Search donors by location
     * @param location Location to search
     * @return List of matching donors
     */
    public List<Donor> searchByLocation(String location) {
        List<Donor> donors = new ArrayList<>();
        String query = "SELECT * FROM donor WHERE location LIKE ? ORDER BY registration_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + location + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                donors.add(extractDonorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching donors by location: " + e.getMessage());
            e.printStackTrace();
        }
        
        return donors;
    }
    
    /**
     * Update donor information
     * @param donor Donor object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateDonor(Donor donor) {
        String query = "UPDATE donor SET name = ?, age = ?, gender = ?, blood_group = ?, " +
                      "organ = ?, contact = ?, location = ? WHERE donor_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, donor.getName());
            pstmt.setInt(2, donor.getAge());
            pstmt.setString(3, donor.getGender());
            pstmt.setString(4, donor.getBloodGroup());
            pstmt.setString(5, donor.getOrgan());
            pstmt.setString(6, donor.getContact());
            pstmt.setString(7, donor.getLocation());
            pstmt.setInt(8, donor.getDonorId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating donor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Get donor count statistics
     * @return Total number of donors
     */
    public int getDonorCount() {
        String query = "SELECT COUNT(*) FROM donor";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting donor count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Extract Donor object from ResultSet
     * @param rs ResultSet
     * @return Donor object
     * @throws SQLException
     */
    private Donor extractDonorFromResultSet(ResultSet rs) throws SQLException {
        return new Donor(
            rs.getInt("donor_id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("gender"),
            rs.getString("blood_group"),
            rs.getString("organ"),
            rs.getString("contact"),
            rs.getString("location")
        );
    }
}
