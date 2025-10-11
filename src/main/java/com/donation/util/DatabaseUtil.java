package com.donation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for database operations
 * Manages database connections and initialization
 */
public class DatabaseUtil {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "donation_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Set your database password here
    
    private static Connection connection = null;
    
    /**
     * Initialize the database connection and create tables if they don't exist
     */
    public static void initializeDatabase() throws SQLException {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // First connect without database name to check if database exists
            try (Connection tempConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = tempConn.createStatement()) {
                
                // Create database if it doesn't exist
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                LOGGER.info("Database checked/created successfully");
            }
            
            // Connect to the specific database
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            LOGGER.info("Connected to database successfully");
            
            // Create tables
            createTables();
            
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    /**
     * Get a connection to the database
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing database connection", e);
            }
        }
    }
    
    /**
     * Create necessary database tables
     */
    private static void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Users table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "role VARCHAR(20) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            
            // Donors table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS donors (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "date_of_birth DATE NOT NULL, " +
                "gender VARCHAR(10) NOT NULL, " +
                "blood_type VARCHAR(5), " +
                "address VARCHAR(255) NOT NULL, " +
                "city VARCHAR(50) NOT NULL, " +
                "state VARCHAR(50) NOT NULL, " +
                "zip_code VARCHAR(20) NOT NULL, " +
                "phone VARCHAR(20) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "medical_history TEXT, " +
                "is_blood_donor BOOLEAN DEFAULT FALSE, " +
                "is_organ_donor BOOLEAN DEFAULT FALSE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")"
            );
            
            // Blood donations table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS blood_donations (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "donor_id INT NOT NULL, " +
                "donation_date DATE NOT NULL, " +
                "quantity_ml INT NOT NULL, " +
                "hemoglobin_level FLOAT, " +
                "blood_pressure VARCHAR(20), " +
                "pulse_rate INT, " +
                "donation_center VARCHAR(100) NOT NULL, " +
                "staff_id INT, " +
                "notes TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (donor_id) REFERENCES donors(id)" +
                ")"
            );
            
            // Organ donations table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS organ_donations (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "donor_id INT NOT NULL, " +
                "organ_type VARCHAR(50) NOT NULL, " +
                "registration_date DATE NOT NULL, " +
                "consent_document VARCHAR(255), " +
                "medical_evaluation_status VARCHAR(50), " +
                "notes TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (donor_id) REFERENCES donors(id)" +
                ")"
            );
            
            // Charity donations table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS charity_donations (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "donor_id INT NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, " +
                "donation_date DATE NOT NULL, " +
                "payment_method VARCHAR(50) NOT NULL, " +
                "transaction_id VARCHAR(100), " +
                "purpose VARCHAR(255), " +
                "is_anonymous BOOLEAN DEFAULT FALSE, " +
                "receipt_sent BOOLEAN DEFAULT FALSE, " +
                "notes TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (donor_id) REFERENCES donors(id)" +
                ")"
            );
            
            // Donation schedules table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS donation_schedules (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "donor_id INT NOT NULL, " +
                "donation_type VARCHAR(20) NOT NULL, " +
                "scheduled_date DATE NOT NULL, " +
                "scheduled_time TIME NOT NULL, " +
                "location VARCHAR(100) NOT NULL, " +
                "status VARCHAR(20) DEFAULT 'SCHEDULED', " +
                "reminder_sent BOOLEAN DEFAULT FALSE, " +
                "notes TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (donor_id) REFERENCES donors(id)" +
                ")"
            );
            
            LOGGER.info("Database tables created successfully");
        }
    }
}