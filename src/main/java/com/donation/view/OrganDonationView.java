package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.model.Donor;
import com.donation.model.OrganDonation;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * View for organ donation registration
 */
public class OrganDonationView extends JFrame {
    private final AuthController authController;
    
    // Form fields
    private JTextField donorIdField;
    private JTextField donorNameField;
    private JComboBox<String> organTypeComboBox;
    private JCheckBox consentCheckBox;
    private JComboBox<String> evaluationStatusComboBox;
    private JTextArea notesArea;
    
    public OrganDonationView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Organ Donation Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header
        JLabel titleLabel = ThemeUtil.createTitleLabel("Organ Donation Registration");
        
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
        
        // Donation Details Section
        JLabel sectionLabel2 = ThemeUtil.createHeaderLabel("Organ Donation Details");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabel2, gbc);
        
        // Organ Type
        JLabel organTypeLabel = new JLabel("Organ Type:");
        String[] organTypes = {"Kidney", "Liver", "Heart", "Lung", "Pancreas", "Intestine", "Cornea", "Bone", "Skin", "Heart Valve"};
        organTypeComboBox = new JComboBox<>(organTypes);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(organTypeLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(organTypeComboBox, gbc);
        
        // Consent Document
        JLabel consentLabel = new JLabel("Consent Document:");
        consentCheckBox = new JCheckBox("Consent form signed");
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(consentLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(consentCheckBox, gbc);
        
        // Medical Evaluation Status
        JLabel evaluationLabel = new JLabel("Medical Evaluation Status:");
        String[] statuses = {"Not Started", "In Progress", "Completed - Eligible", "Completed - Not Eligible"};
        evaluationStatusComboBox = new JComboBox<>(statuses);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(evaluationLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(evaluationStatusComboBox, gbc);
        
        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        notesArea = new JTextArea(5, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(notesLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        // Information Panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(240, 248, 255));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("<html><b>Important Information:</b><br>" +
                "Organ donation is a life-saving gift. The donor must be fully informed about the process " +
                "and provide explicit consent. Medical evaluation is required to determine eligibility.</html>");
        infoLabel.setFont(ThemeUtil.SMALL_FONT);
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(infoPanel, gbc);
        
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
        
        JButton saveButton = ThemeUtil.createStyledButton("Register Donation");
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
                
                // Populate fields
                donorNameField.setText(donor.getFirstName() + " " + donor.getLastName());
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
            !consentCheckBox.isSelected()) {
            
            JOptionPane.showMessageDialog(this, 
                "Please fill all required fields and ensure consent is provided", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create organ donation object
            OrganDonation donation = new OrganDonation();
            donation.setDonorId(Integer.parseInt(donorIdField.getText().trim()));
            donation.setOrganType((String) organTypeComboBox.getSelectedItem());
            donation.setRegistrationDate(new Date(System.currentTimeMillis())); // Current date
            donation.setConsentDocument(consentCheckBox.isSelected() ? "Signed" : "Not Signed");
            donation.setMedicalEvaluationStatus((String) evaluationStatusComboBox.getSelectedItem());
            donation.setNotes(notesArea.getText().trim());
            
            // TODO: Save to database using DAO
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Organ donation registration successful!\nOrgan Type: " + donation.getOrganType() +
                "\nEvaluation Status: " + donation.getMedicalEvaluationStatus(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close the form
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid Donor ID", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}