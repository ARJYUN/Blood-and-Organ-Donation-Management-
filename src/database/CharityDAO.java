package database;

import models.CharityRequest;
import models.Donation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Charity operations
 * Handles CRUD operations for charity requests and donations
 */
public class CharityDAO {
    
    /**
     * Create a new charity request
     * @param request CharityRequest object
     * @return Generated request ID if successful, -1 otherwise
     */
    public int createCharityRequest(CharityRequest request) {
        String query = "INSERT INTO charity_request (title, description, requester_name, type, goal_amount) " +
                      "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setString(3, request.getRequesterName());
            pstmt.setString(4, request.getType());
            pstmt.setDouble(5, request.getGoalAmount());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating charity request: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Get all active charity requests
     * @return List of active charity requests
     */
    public List<CharityRequest> getActiveCharityRequests() {
        List<CharityRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM charity_request WHERE status = 'ACTIVE' ORDER BY created_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                requests.add(extractCharityRequestFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching active charity requests: " + e.getMessage());
            e.printStackTrace();
        }
        
        return requests;
    }
    
    /**
     * Get all charity requests
     * @return List of all charity requests
     */
    public List<CharityRequest> getAllCharityRequests() {
        List<CharityRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM charity_request ORDER BY created_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                requests.add(extractCharityRequestFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all charity requests: " + e.getMessage());
            e.printStackTrace();
        }
        
        return requests;
    }
    
    /**
     * Get charity request by ID
     * @param requestId Request ID
     * @return CharityRequest object if found, null otherwise
     */
    public CharityRequest getCharityRequestById(int requestId) {
        String query = "SELECT * FROM charity_request WHERE request_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCharityRequestFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching charity request: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Add a donation to a charity request
     * @param donation Donation object
     * @return true if successful, false otherwise
     */
    public boolean addDonation(Donation donation) {
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // Insert donation
            String insertQuery = "INSERT INTO donation (donor_name, request_id, amount, payment_method) " +
                               "VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, donation.getDonorName());
                pstmt.setInt(2, donation.getRequestId());
                pstmt.setDouble(3, donation.getAmount());
                pstmt.setString(4, donation.getPaymentMethod());
                
                pstmt.executeUpdate();
            }
            
            // Update raised amount in charity request
            String updateQuery = "UPDATE charity_request SET raised_amount = raised_amount + ? " +
                               "WHERE request_id = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, donation.getAmount());
                pstmt.setInt(2, donation.getRequestId());
                
                pstmt.executeUpdate();
            }
            
            conn.commit(); // Commit transaction
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error adding donation: " + e.getMessage());
            e.printStackTrace();
            
            // Rollback transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // Reset auto-commit
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }
    
    /**
     * Get donations for a specific charity request
     * @param requestId Request ID
     * @return List of donations
     */
    public List<Donation> getDonationsByRequestId(int requestId) {
        List<Donation> donations = new ArrayList<>();
        String query = "SELECT * FROM donation WHERE request_id = ? ORDER BY donation_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                donations.add(extractDonationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching donations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return donations;
    }
    
    /**
     * Update charity request status
     * @param requestId Request ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateCharityRequestStatus(int requestId, String status) {
        String query = "UPDATE charity_request SET status = ? WHERE request_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, requestId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating charity request status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Get total donations amount
     * @return Total donations amount
     */
    public double getTotalDonationsAmount() {
        String query = "SELECT SUM(amount) FROM donation";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total donations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    /**
     * Extract CharityRequest object from ResultSet
     */
    private CharityRequest extractCharityRequestFromResultSet(ResultSet rs) throws SQLException {
        return new CharityRequest(
            rs.getInt("request_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("requester_name"),
            rs.getString("type"),
            rs.getDouble("goal_amount"),
            rs.getDouble("raised_amount"),
            rs.getTimestamp("created_date"),
            rs.getString("status")
        );
    }
    
    /**
     * Extract Donation object from ResultSet
     */
    private Donation extractDonationFromResultSet(ResultSet rs) throws SQLException {
        return new Donation(
            rs.getInt("donation_id"),
            rs.getString("donor_name"),
            rs.getInt("request_id"),
            rs.getDouble("amount"),
            rs.getTimestamp("donation_date"),
            rs.getString("payment_method")
        );
    }
}
