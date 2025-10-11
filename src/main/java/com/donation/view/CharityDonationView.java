package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.model.CharityDonation;
import com.donation.model.Donor;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * View for charity donation registration
 */
public class CharityDonationView extends JFrame {
    private final AuthController authController;
    
    // Form fields
    private JTextField donorIdField;
    private JTextField donorNameField;
    private JTextField amountField;
    private JComboBox<String> paymentMethodComboBox;
    private JTextField transactionIdField;
    private JTextField purposeField;
    private JCheckBox anonymousCheckBox;
    private JCheckBox receiptSentCheckBox;
    private JTextArea notesArea;
    
    public CharityDonationView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Charity Donation Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header
        JLabel titleLabel = ThemeUtil.createTitleLabel("Charity Donation Registration");
        
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
        JLabel sectionLabel2 = ThemeUtil.createHeaderLabel("Donation Details");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabel2, gbc);
        
        // Amount
        JLabel amountLabel = new JLabel("Amount ($):");
        amountField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);
        
        // Payment Method
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        String[] paymentMethods = {"Credit Card", "Debit Card", "Bank Transfer", "PayPal", "Check", "Cash"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(paymentMethodLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(paymentMethodComboBox, gbc);
        
        // Transaction ID
        JLabel transactionIdLabel = new JLabel("Transaction ID:");
        transactionIdField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(transactionIdLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(transactionIdField, gbc);
        
        // Purpose
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(purposeLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(purposeField, gbc);
        
        // Options Section
        JLabel sectionLabel3 = ThemeUtil.createHeaderLabel("Options");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabel3, gbc);
        
        // Anonymous donation
        anonymousCheckBox = new JCheckBox("Anonymous donation");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(anonymousCheckBox, gbc);
        
        // Receipt sent
        receiptSentCheckBox = new JCheckBox("Receipt sent");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(receiptSentCheckBox, gbc);
        
        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        notesArea = new JTextArea(5, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        formPanel.add(notesLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        // Information Panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(240, 248, 255));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("<html><b>Thank you for your generosity!</b><br>" +
                "Your charity donation helps us maintain our facilities, support our staff, " +
                "and reach more people in need. All donations are tax-deductible and you will " +
                "receive a receipt for your records.</html>");
        infoLabel.setFont(ThemeUtil.SMALL_FONT);
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 12;
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
        
        JButton saveButton = ThemeUtil.createStyledButton("Process Donation");
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
            amountField.getText().trim().isEmpty() ||
            purposeField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Please fill all required fields: Donor ID, Amount, and Purpose", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create charity donation object
            CharityDonation donation = new CharityDonation();
            donation.setDonorId(Integer.parseInt(donorIdField.getText().trim()));
            donation.setAmount(new BigDecimal(amountField.getText().trim()));
            donation.setDonationDate(new Date(System.currentTimeMillis())); // Current date
            donation.setPaymentMethod((String) paymentMethodComboBox.getSelectedItem());
            donation.setTransactionId(transactionIdField.getText().trim());
            donation.setPurpose(purposeField.getText().trim());
            donation.setAnonymous(anonymousCheckBox.isSelected());
            donation.setReceiptSent(receiptSentCheckBox.isSelected());
            donation.setNotes(notesArea.getText().trim());
            
            // TODO: Save to database using DAO
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Charity donation processed successfully!\nAmount: $" + donation.getAmount() +
                "\nPurpose: " + donation.getPurpose() +
                "\nPayment Method: " + donation.getPaymentMethod(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close the form
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid amount", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
