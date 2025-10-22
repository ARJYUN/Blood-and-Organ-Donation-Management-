package com.blooddonation.main;

import com.blooddonation.view.LoginForm;
import com.blooddonation.database.DatabaseConnection;

import javax.swing.*;

/**
 * Main class to start the Blood Donation Management System
 * Initializes the database connection and launches the login form
 */
public class Main {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
        
        // Test database connection
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        if (!dbConnection.testConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Database connection failed!\n" +
                "Please ensure MySQL is running and the database is created.\n" +
                "Check the database.properties file for correct configuration.",
                "Database Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Launch the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginForm().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, 
                        "Error starting application: " + e.getMessage(),
                        "Application Error", 
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
