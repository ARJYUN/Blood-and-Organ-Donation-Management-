package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.model.BloodDonation;
import com.donation.model.Donor;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * View for blood donation registration
 */
public class BloodDonationView extends JFrame {
    private final AuthController authController;
    
    // Form fields
    private JTextField donorIdField;
    private JTextField donorNameField;
    private JTextField bloodTypeField;
    private JTextField quantityField;
    private JTextField hemoglobinField;
    private JTextField bloodPressureField;
    private JTextField pulseRateField;
    private JTextField donationCenterField;
    private JTextField staffIdField;
    private JTextArea notesArea;
    
    public BloodDonationView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Blood Donation Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header
        JLabel titleLabel = ThemeUtil.createTitleLabel("Blood Donation Registration");
        
        // Create form panel
        JPanel formPanel = createFormPanel();
        
        // Create buttons panel
        JPanel buttonsPanel = createButtonsPanel();
        
        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Donor Information Section
        JLabel sectionLabel1 = ThemeUtil.createHeaderLabel("Donor Information");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabel1, gbc);
        
        // Donor ID
        JLabel donorIdLabel = new JLabel("Donor ID:");
        donorIdField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(donorIdLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(donorIdField, gbc);
        
        // Search button
        JButton searchButton = ThemeUtil.createStyledButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDonor();
            }
        });
        
        gbc.gridx = 2;
        formPanel.add(searchButton, gbc);
        
        // Donor Name
        JLabel donorNameLabel = new JLabel("Donor Name:");
        donorNameField = new JTextField(20);
        donorNameField.setEditable(false);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(donorNameLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(donorNameField, gbc);
        
        // Blood Type
        JLabel bloodTypeLabel = new JLabel("Blood Type:");
        bloodTypeField = new JTextField(20);
        bloodTypeField.setEditable(false);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(bloodTypeLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(bloodTypeField, gbc);
        
        // Donation Details Section
        JLabel sectionLabel2 = ThemeUtil.createHeaderLabel("Donation Details");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabel2, gbc);
        
        // Quantity
        JLabel quantityLabel = new JLabel("Quantity (ml):");
        quantityField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(quantityLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(quantityField, gbc);
        
        // Hemoglobin Level
        JLabel hemoglobinLabel = new JLabel("Hemoglobin Level (g/dL):");
        hemoglobinField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(hemoglobinLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(hemoglobinField, gbc);
        
        // Blood Pressure
        JLabel bloodPressureLabel = new JLabel("Blood Pressure (mmHg):");
        bloodPressureField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(bloodPressureLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(bloodPressureField, gbc);
        
        // Pulse Rate
        JLabel pulseRateLabel = new JLabel("Pulse Rate (bpm):");
        pulseRateField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(pulseRateLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(pulseRateField, gbc);
        
        // Donation Center
        JLabel donationCenterLabel = new JLabel("Donation Center:");
        donationCenterField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(donationCenterLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(donationCenterField, gbc);
        
        // Staff ID
        JLabel staffIdLabel = new JLabel("Staff ID:");
        staffIdField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(staffIdLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(staffIdField, gbc);
        
        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        notesArea = new JTextArea(5, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(notesLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        return formPanel;
    }
    
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(ThemeUtil.REGULAR_FONT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JButton saveButton = ThemeUtil.createStyledButton("Save Donation");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDonation();
            }
        });
        
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);
        
        return buttonsPanel;
    }
    
    private void searchDonor() {
        String donorId = donorIdField.getText().trim();
        
        if (donorId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Donor ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // TODO: Implement actual donor search using DAO
        // For now, use mock data
        try {
            int id = Integer.parseInt(donorId);
            
            // Mock donor data
            if (id > 0) {
                Donor donor = new Donor();
                donor.setId(id);
                donor.setFirstName("John");
                donor.setLastName("Doe");
                donor.setBloodType("A+");
                
                // Populate fields
                donorNameField.setText(donor.getFirstName() + " " + donor.getLastName());
                bloodTypeField.setText(donor.getBloodType());
            } else {
                JOptionPane.showMessageDialog(this, "Donor not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Donor ID format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveDonation() {
        // Validate required fields
        if (donorIdField.getText().trim().isEmpty() ||
            donorNameField.getText().trim().isEmpty() ||
            quantityField.getText().trim().isEmpty() ||
            donationCenterField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Please fill all required fields: Donor ID, Quantity, and Donation Center", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create blood donation object
            BloodDonation donation = new BloodDonation();
            donation.setDonorId(Integer.parseInt(donorIdField.getText().trim()));
            donation.setDonationDate(new Date(System.currentTimeMillis())); // Current date
            donation.setQuantityMl(Integer.parseInt(quantityField.getText().trim()));
            
            if (!hemoglobinField.getText().trim().isEmpty()) {
                donation.setHemoglobinLevel(Float.parseFloat(hemoglobinField.getText().trim()));
            }
            
            donation.setBloodPressure(bloodPressureField.getText().trim());
            
            if (!pulseRateField.getText().trim().isEmpty()) {
                donation.setPulseRate(Integer.parseInt(pulseRateField.getText().trim()));
            }
            
            donation.setDonationCenter(donationCenterField.getText().trim());
            
            if (!staffIdField.getText().trim().isEmpty()) {
                donation.setStaffId(Integer.parseInt(staffIdField.getText().trim()));
            }
            
            donation.setNotes(notesArea.getText().trim());
            
            // TODO: Save to database using DAO
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Blood donation recorded successfully!\nDonation Date: " + 
                new SimpleDateFormat("yyyy-MM-dd").format(donation.getDonationDate()), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close the form
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numeric values for Donor ID, Quantity, Hemoglobin Level, Pulse Rate, and Staff ID", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}