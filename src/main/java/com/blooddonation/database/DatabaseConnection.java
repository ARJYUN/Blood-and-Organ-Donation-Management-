package com.blooddonation.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Database connection manager using singleton pattern
 * Handles MySQL database connections for the Blood Donation Management System
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;
    private static Properties properties;
    
    private DatabaseConnection() {
        loadProperties();
    }
    
    /**
     * Get singleton instance of DatabaseConnection
     * @return DatabaseConnection instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Load database properties from configuration file
     */
    private void loadProperties() {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties");
        try {
            if (input != null) {
                properties.load(input);
                return;
            }
            String explicitPath = System.getProperty("db.config.path");
            if (explicitPath == null || explicitPath.trim().isEmpty()) {
                explicitPath = System.getenv("DB_CONFIG_PATH");
            }
            if (explicitPath != null && !explicitPath.trim().isEmpty()) {
                try (InputStream fis = new FileInputStream(explicitPath)) {
                    properties.load(fis);
                    return;
                }
            }
            Path[] candidates = new Path[] {
                Paths.get("database.properties"),
                Paths.get("src", "main", "resources", "database.properties"),
                Paths.get("Blood-and-Organ-Donation-Management", "src", "main", "resources", "database.properties")
            };
            for (Path p : candidates) {
                if (Files.exists(p)) {
                    try (InputStream fis = Files.newInputStream(p)) {
                        properties.load(fis);
                        return;
                    }
                }
            }
            throw new RuntimeException("Unable to find database.properties file");
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {}
            }
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                String driver = properties.getProperty("db.driver");
                String url = properties.getProperty("db.url");
                String username = System.getProperty("db.username");
                if (username == null || username.trim().isEmpty()) {
                    username = System.getenv("DB_USERNAME");
                }
                if (username == null || username.trim().isEmpty()) {
                    username = properties.getProperty("db.username");
                }
                String password = System.getProperty("db.password");
                if (password == null || password.trim().isEmpty()) {
                    password = System.getenv("DB_PASSWORD");
                }
                if (password == null) {
                    password = properties.getProperty("db.password");
                }
                Class.forName(driver);
                try {
                    connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    // If database doesn't exist, try to create it
                    if (e.getMessage().contains("Unknown database")) {
                        System.out.println("Database not found. Attempting to create it...");
                        createDatabaseIfNotExists(url, username, password);
                        connection = DriverManager.getConnection(url, username, password);
                    } else {
                        throw e;
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Create database if it doesn't exist
     * @param fullUrl Full database URL with database name
     * @param username Database username
     * @param password Database password
     * @throws SQLException if database creation fails
     */
    private void createDatabaseIfNotExists(String fullUrl, String username, String password) throws SQLException {
        // Extract database name and server URL
        String dbName = "blood_donation_db";
        String serverUrl = fullUrl.substring(0, fullUrl.lastIndexOf('/'));
        int paramIndex = fullUrl.indexOf('?');
        if (paramIndex > 0) {
            String params = fullUrl.substring(paramIndex);
            serverUrl = serverUrl + "/" + params;
        }
        
        try (Connection tempConn = DriverManager.getConnection(serverUrl, username, password);
             Statement stmt = tempConn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database '" + dbName + "' created successfully.");
            
            // Initialize schema
            initializeSchema(tempConn, dbName);
        }
    }
    
    /**
     * Initialize database schema
     * @param conn Connection to MySQL server
     * @param dbName Database name
     */
    private void initializeSchema(Connection conn, String dbName) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("USE " + dbName);
            
            // Execute schema creation
            executeSchemaSql(stmt);
            System.out.println("Database schema initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Warning: Could not initialize schema automatically: " + e.getMessage());
            System.err.println("Please run sql/schema.sql manually to create tables.");
        }
    }
    
    /**
     * Execute schema SQL statements
     * @param stmt Statement to execute SQL
     * @throws SQLException if execution fails
     */
    private void executeSchemaSql(Statement stmt) throws SQLException {
        // Create users table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "email VARCHAR(100) UNIQUE NOT NULL," +
            "password VARCHAR(255) NOT NULL," +
            "role ENUM('ADMIN', 'DONOR', 'RECEIVER', 'CHARITY') NOT NULL," +
            "location VARCHAR(100)," +
            "phone VARCHAR(20)," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ")");
        
        // Create hospitals table (must be before receivers due to foreign key)
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS hospitals (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "address VARCHAR(200) NOT NULL," +
            "contact_number VARCHAR(20)," +
            "email VARCHAR(100)," +
            "specialization VARCHAR(200)," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")");
        
        // Create donors table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS donors (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "user_id INT NOT NULL," +
            "blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL," +
            "organ_type ENUM('BLOOD', 'KIDNEY', 'LIVER', 'HEART', 'LUNG', 'PANCREAS', 'EYE', 'BONE_MARROW') NOT NULL," +
            "availability_status ENUM('AVAILABLE', 'UNAVAILABLE', 'DONATED') DEFAULT 'AVAILABLE'," +
            "last_donation_date DATE," +
            "medical_clearance BOOLEAN DEFAULT FALSE," +
            "notes TEXT," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
            ")");
        
        // Create receivers table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS receivers (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "user_id INT NOT NULL," +
            "required_blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL," +
            "required_organ ENUM('BLOOD', 'KIDNEY', 'LIVER', 'HEART', 'LUNG', 'PANCREAS', 'EYE', 'BONE_MARROW') NOT NULL," +
            "urgency_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM'," +
            "status ENUM('PENDING', 'APPROVED', 'REJECTED', 'FULFILLED') DEFAULT 'PENDING'," +
            "medical_condition TEXT," +
            "hospital_id INT," +
            "request_date DATE DEFAULT (CURRENT_DATE)," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE," +
            "FOREIGN KEY (hospital_id) REFERENCES hospitals(id) ON DELETE SET NULL" +
            ")");
        
        // Create charities table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS charities (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "user_id INT NOT NULL," +
            "organization_name VARCHAR(100) NOT NULL," +
            "event_name VARCHAR(100) NOT NULL," +
            "event_date DATE NOT NULL," +
            "event_location VARCHAR(100) NOT NULL," +
            "contact_info VARCHAR(100)," +
            "description TEXT," +
            "status ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'ACTIVE'," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
            ")");
        
        // Create donation_requests table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS donation_requests (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "donor_id INT NOT NULL," +
            "receiver_id INT NOT NULL," +
            "status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING'," +
            "request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "response_date TIMESTAMP NULL," +
            "notes TEXT," +
            "FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE," +
            "FOREIGN KEY (receiver_id) REFERENCES receivers(id) ON DELETE CASCADE" +
            ")");
        
        // Create charity_participants table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS charity_participants (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "charity_id INT NOT NULL," +
            "user_id INT NOT NULL," +
            "participation_type ENUM('DONOR', 'VOLUNTEER', 'RECEIVER') NOT NULL," +
            "status ENUM('REGISTERED', 'CONFIRMED', 'CANCELLED') DEFAULT 'REGISTERED'," +
            "registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (charity_id) REFERENCES charities(id) ON DELETE CASCADE," +
            "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
            ")");
        
        // Create indexes
        try {
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_users_email ON users(email)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_users_role ON users(role)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_donors_blood_group ON donors(blood_group)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_donors_organ_type ON donors(organ_type)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_donors_availability ON donors(availability_status)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_receivers_blood_group ON receivers(required_blood_group)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_receivers_organ ON receivers(required_organ)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_receivers_status ON receivers(status)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_charities_event_date ON charities(event_date)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_hospitals_name ON hospitals(name)");
        } catch (SQLException e) {
            // Indexes might already exist, ignore errors
        }
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try {
            Connection testConn = getConnection();
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
