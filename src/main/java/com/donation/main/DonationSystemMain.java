package com.donation.main;

import com.donation.view.LoginView;
import com.donation.util.DatabaseUtil;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the Blood and Organ Donation Management System
 * This class initializes the application and sets up the UI
 */
public class DonationSystemMain {
    private static final Logger LOGGER = Logger.getLogger(DonationSystemMain.class.getName());

    public static void main(String[] args) {
        // Set up logging
        LOGGER.info("Starting Blood and Organ Donation Management System");
        
        // Initialize database connection
        try {
            DatabaseUtil.initializeDatabase();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to initialize database, running in demo mode", e);
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, 
                    "Database not available. Running in demo mode.\nSome features may not work correctly.",
                    "Database Warning", JOptionPane.WARNING_MESSAGE);
            });
        }
        
        // Set up the UI look and feel
        SwingUtilities.invokeLater(() -> {
            try {
                // Apply custom theme
                ThemeUtil.setLookAndFeel();
                
                // Start with login screen
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                
                LOGGER.info("Application UI initialized successfully");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize UI", e);
                JOptionPane.showMessageDialog(null, 
                    "Failed to initialize application UI.",
                    "UI Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}