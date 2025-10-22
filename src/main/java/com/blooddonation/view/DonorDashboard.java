package com.blooddonation.view;

import com.blooddonation.model.User;
import com.blooddonation.model.Donor;
import com.blooddonation.database.DonorDAO;
import com.blooddonation.database.ReceiverDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Donor Dashboard
 * Provides interface for donors to manage their donation information
 */
public class DonorDashboard extends JFrame {
    private User currentUser;
    private DonorDAO donorDAO;
    private ReceiverDAO receiverDAO;
    private JTable donorsTable;
    private JTable receiversTable;
    private DefaultTableModel donorsModel;
    private DefaultTableModel receiversModel;
    
    public DonorDashboard(User user) {
        this.currentUser = user;
        this.donorDAO = new DonorDAO();
        this.receiverDAO = new ReceiverDAO();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Donor Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    /**
     * Initialize dashboard components
     */
    private void initializeComponents() {
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + " (Donor)");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // My Donations tab
        tabbedPane.addTab("My Donations", createMyDonationsPanel());
        
        // Available Requests tab
        tabbedPane.addTab("Available Requests", createAvailableRequestsPanel());
        
        // Add Donation tab
        tabbedPane.addTab("Add New Donation", createAddDonationPanel());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Setup logout button
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                this.dispose();
            }
        });
    }
    
    /**
     * Setup dashboard layout
     */
    private void setupLayout() {
        // Layout is handled in initializeComponents
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Event handlers are set up in individual components
    }
    
    /**
     * Create my donations panel
     */
    private JPanel createMyDonationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Donations table
        String[] columns = {"ID", "Blood Group", "Organ Type", "Status", "Medical Clearance", "Last Donation", "Notes"};
        donorsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorsTable = new JTable(donorsModel);
        JScrollPane scrollPane = new JScrollPane(donorsTable);
        
        // Load my donations
        loadMyDonations();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create available requests panel
     */
    private JPanel createAvailableRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Requests table
        String[] columns = {"ID", "Required Blood", "Required Organ", "Urgency", "Status", "Hospital ID", "Request Date"};
        receiversModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        receiversTable = new JTable(receiversModel);
        JScrollPane scrollPane = new JScrollPane(receiversTable);
        
        // Load available requests
        loadAvailableRequests();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create add donation panel
     */
    private JPanel createAddDonationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Blood Group
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        JComboBox<String> bloodGroupCombo = new JComboBox<>(bloodGroups);
        bloodGroupCombo.setPreferredSize(new Dimension(150, 25));
        
        // Organ Type
        JLabel organTypeLabel = new JLabel("Organ Type:");
        String[] organTypes = {"Blood", "Kidney", "Liver", "Heart", "Lung", "Pancreas", "Eye", "Bone Marrow"};
        JComboBox<String> organTypeCombo = new JComboBox<>(organTypes);
        organTypeCombo.setPreferredSize(new Dimension(150, 25));
        
        // Medical Clearance
        JLabel medicalClearanceLabel = new JLabel("Medical Clearance:");
        JCheckBox medicalClearanceCheck = new JCheckBox("I have medical clearance");
        
        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        JTextArea notesArea = new JTextArea(3, 30);
        notesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Add Donation button
        JButton addDonationButton = new JButton("Add Donation");
        addDonationButton.setBackground(new Color(34, 139, 34));
        addDonationButton.setForeground(Color.WHITE);
        addDonationButton.setPreferredSize(new Dimension(120, 30));
        
        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(bloodGroupLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(bloodGroupCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(organTypeLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(organTypeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(medicalClearanceLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(medicalClearanceCheck, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(notesLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(notesArea, gbc);
        
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addDonationButton, gbc);
        
        // Add donation button action
        addDonationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewDonation(bloodGroupCombo, organTypeCombo, medicalClearanceCheck, notesArea);
            }
        });
        
        return panel;
    }
    
    /**
     * Load my donations data
     */
    private void loadMyDonations() {
        donorsModel.setRowCount(0);
        List<Donor> donors = donorDAO.findByUserId(currentUser.getId());
        for (Donor donor : donors) {
            Object[] row = {
                donor.getId(),
                donor.getBloodGroup().getDisplayName(),
                donor.getOrganType().getDisplayName(),
                donor.getAvailabilityStatus().getDisplayName(),
                donor.isMedicalClearance() ? "Yes" : "No",
                donor.getLastDonationDate() != null ? donor.getLastDonationDate().toString() : "Never",
                donor.getNotes() != null ? donor.getNotes() : ""
            };
            donorsModel.addRow(row);
        }
    }
    
    /**
     * Load available requests data
     */
    private void loadAvailableRequests() {
        receiversModel.setRowCount(0);
        List<com.blooddonation.model.Receiver> receivers = receiverDAO.getAllReceivers();
        for (com.blooddonation.model.Receiver receiver : receivers) {
            Object[] row = {
                receiver.getId(),
                receiver.getRequiredBloodGroup().getDisplayName(),
                receiver.getRequiredOrgan().getDisplayName(),
                receiver.getUrgencyLevel().getDisplayName(),
                receiver.getStatus().getDisplayName(),
                receiver.getHospitalId(),
                receiver.getRequestDate().toString()
            };
            receiversModel.addRow(row);
        }
    }
    
    /**
     * Add new donation
     */
    private void addNewDonation(JComboBox<String> bloodGroupCombo, JComboBox<String> organTypeCombo, 
                               JCheckBox medicalClearanceCheck, JTextArea notesArea) {
        String bloodGroupStr = (String) bloodGroupCombo.getSelectedItem();
        String organTypeStr = (String) organTypeCombo.getSelectedItem();
        boolean medicalClearance = medicalClearanceCheck.isSelected();
        String notes = notesArea.getText().trim();
        
        // Convert strings to enums
        Donor.BloodGroup bloodGroup = Donor.BloodGroup.valueOf(bloodGroupStr.replace("+", "_POSITIVE").replace("-", "_NEGATIVE"));
        Donor.OrganType organType = Donor.OrganType.valueOf(organTypeStr.toUpperCase().replace(" ", "_"));
        
        // Create donor object
        Donor donor = new Donor(currentUser.getId(), bloodGroup, organType, Donor.AvailabilityStatus.AVAILABLE);
        donor.setMedicalClearance(medicalClearance);
        donor.setNotes(notes.isEmpty() ? null : notes);
        
        // Save to database
        if (donorDAO.createDonor(donor)) {
            JOptionPane.showMessageDialog(this, "Donation added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            bloodGroupCombo.setSelectedIndex(0);
            organTypeCombo.setSelectedIndex(0);
            medicalClearanceCheck.setSelected(false);
            notesArea.setText("");
            
            // Refresh my donations table
            loadMyDonations();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add donation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
