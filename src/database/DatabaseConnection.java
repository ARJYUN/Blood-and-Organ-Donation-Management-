package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection class for managing MySQL database connections
 * Singleton pattern implementation
 */
public class DatabaseConnection {
    // Database credentials - UPDATE THESE WITH YOUR MYSQL CREDENTIALS
    private static final String URL = "jdbc:mysql://localhost:3306/blood_organ_donation";
    private static final String USER = "root";
    private static final String PASSWORD = "5891"; // Update with your MySQL password
    
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }
    
    /**
     * Get database connection (Singleton pattern)
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection!");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
