import gui.LoginFrame;
import database.DatabaseConnection;

import javax.swing.*;

/**
 * Main Application Entry Point
 * Blood and Organ Donation Management System
 * 
 * This application demonstrates:
 * - Encapsulation: Private attributes with getters/setters in model classes
 * - Inheritance: Donor and Receptionist extend User base class
 * - Polymorphism: Method overriding and interface implementations
 * - Abstraction: PaymentGateway interface with multiple implementations
 */
public class MainApplication {
    
    public static void main(String[] args) {
        // Test database connection
        System.out.println("===========================================");
        System.out.println("Blood & Organ Donation Management System");
        System.out.println("===========================================");
        System.out.println("Testing database connection...");
        
        if (DatabaseConnection.testConnection()) {
            System.out.println("✓ Database connection successful!");
            System.out.println("Starting application...\n");
            
            // Set Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set Look and Feel: " + e.getMessage());
            }
            
            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
            
        } else {
            System.err.println("✗ Database connection failed!");
            System.err.println("\nPlease ensure:");
            System.err.println("1. MySQL server is running");
            System.err.println("2. Database 'blood_organ_donation' exists");
            System.err.println("3. Database credentials in DatabaseConnection.java are correct");
            System.err.println("4. MySQL JDBC driver is in the classpath");
            
            JOptionPane.showMessageDialog(null, 
                "Database connection failed!\n\n" +
                "Please check:\n" +
                "1. MySQL server is running\n" +
                "2. Database exists (run database_schema.sql)\n" +
                "3. Credentials in DatabaseConnection.java are correct\n" +
                "4. MySQL JDBC driver is available",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
    }
}
