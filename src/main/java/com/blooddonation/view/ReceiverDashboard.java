package com.blooddonation.view;

import com.blooddonation.model.User;
import com.blooddonation.model.Receiver;
import com.blooddonation.database.ReceiverDAO;
import com.blooddonation.database.DonorDAO;
import com.blooddonation.database.HospitalDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Receiver Dashboard
 * Provides interface for receivers to manage their requests
 */
public class ReceiverDashboard extends JFrame {
    private User currentUser;
    private ReceiverDAO receiverDAO;
    private DonorDAO donorDAO;
    private HospitalDAO hospitalDAO;
    private JTable myRequestsTable;
    private JTable availableDonorsTable;
    private DefaultTableModel myRequestsModel;
    private DefaultTableModel availableDonorsModel;
    
    public ReceiverDashboard(User user) {
        this.currentUser = user;
        this.receiverDAO = new ReceiverDAO();
        this.donorDAO = new DonorDAO();
        this.hospitalDAO = new HospitalDAO();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Receiver Dashboard");
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
        headerPanel.setBackground(new Color(255, 140, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + " (Receiver)");
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
        
        // My Requests tab
        tabbedPane.addTab("My Requests", createMyRequestsPanel());
        
        // Available Donors tab
        tabbedPane.addTab("Available Donors", createAvailableDonorsPanel());
        
        // New Request tab
        tabbedPane.addTab("New Request", createNewRequestPanel());
        
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
     * Create my requests panel
     */
    private JPanel createMyRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // My requests table
        String[] columns = {"ID", "Required Blood", "Required Organ", "Urgency", "Status", "Hospital ID", "Request Date"};
        myRequestsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        myRequestsTable = new JTable(myRequestsModel);
        JScrollPane scrollPane = new JScrollPane(myRequestsTable);
        
        // Load my requests
        loadMyRequests();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create available donors panel
     */
    private JPanel createAvailableDonorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Available donors table
        String[] columns = {"ID", "Blood Group", "Organ Type", "Status", "Medical Clearance", "Last Donation"};
        availableDonorsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        availableDonorsTable = new JTable(availableDonorsModel);
        JScrollPane scrollPane = new JScrollPane(availableDonorsTable);
        
        // Load available donors
        loadAvailableDonors();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create new request panel
     */
    private JPanel createNewRequestPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Required Blood Group
        JLabel bloodGroupLabel = new JLabel("Required Blood Group:");
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        JComboBox<String> bloodGroupCombo = new JComboBox<>(bloodGroups);
        bloodGroupCombo.setPreferredSize(new Dimension(150, 25));
        
        // Required Organ
        JLabel organTypeLabel = new JLabel("Required Organ:");
        String[] organTypes = {"Blood", "Kidney", "Liver", "Heart", "Lung", "Pancreas", "Eye", "Bone Marrow"};
        JComboBox<String> organTypeCombo = new JComboBox<>(organTypes);
        organTypeCombo.setPreferredSize(new Dimension(150, 25));
        
        // Urgency Level
        JLabel urgencyLabel = new JLabel("Urgency Level:");
        String[] urgencyLevels = {"Low", "Medium", "High", "Critical"};
        JComboBox<String> urgencyCombo = new JComboBox<>(urgencyLevels);
        urgencyCombo.setPreferredSize(new Dimension(150, 25));
        
        // Hospital
        JLabel hospitalLabel = new JLabel("Hospital:");
        List<com.blooddonation.model.Hospital> hospitals = hospitalDAO.getAllHospitals();
        String[] hospitalNames = new String[hospitals.size()];
        for (int i = 0; i < hospitals.size(); i++) {
            hospitalNames[i] = hospitals.get(i).getName();
        }
        JComboBox<String> hospitalCombo = new JComboBox<>(hospitalNames);
        hospitalCombo.setPreferredSize(new Dimension(200, 25));
        
        // Medical Condition
        JLabel medicalConditionLabel = new JLabel("Medical Condition:");
        JTextArea medicalConditionArea = new JTextArea(3, 30);
        medicalConditionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Submit Request button
        JButton submitRequestButton = new JButton("Submit Request");
        submitRequestButton.setBackground(new Color(255, 140, 0));
        submitRequestButton.setForeground(Color.WHITE);
        submitRequestButton.setPreferredSize(new Dimension(120, 30));
        
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
        panel.add(urgencyLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(urgencyCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(hospitalLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(hospitalCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(medicalConditionLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(medicalConditionArea, gbc);
        
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitRequestButton, gbc);
        
        // Submit request button action
        submitRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitNewRequest(bloodGroupCombo, organTypeCombo, urgencyCombo, hospitalCombo, medicalConditionArea, hospitals);
            }
        });
        
        return panel;
    }
    
    /**
     * Load my requests data
     */
    private void loadMyRequests() {
        myRequestsModel.setRowCount(0);
        List<Receiver> requests = receiverDAO.findByUserId(currentUser.getId());
        for (Receiver request : requests) {
            Object[] row = {
                request.getId(),
                request.getRequiredBloodGroup().getDisplayName(),
                request.getRequiredOrgan().getDisplayName(),
                request.getUrgencyLevel().getDisplayName(),
                request.getStatus().getDisplayName(),
                request.getHospitalId(),
                request.getRequestDate().toString()
            };
            myRequestsModel.addRow(row);
        }
    }
    
    /**
     * Load available donors data
     */
    private void loadAvailableDonors() {
        availableDonorsModel.setRowCount(0);
        List<com.blooddonation.model.Donor> donors = donorDAO.getAllDonors();
        for (com.blooddonation.model.Donor donor : donors) {
            if (donor.getAvailabilityStatus() == com.blooddonation.model.Donor.AvailabilityStatus.AVAILABLE) {
                Object[] row = {
                    donor.getId(),
                    donor.getBloodGroup().getDisplayName(),
                    donor.getOrganType().getDisplayName(),
                    donor.getAvailabilityStatus().getDisplayName(),
                    donor.isMedicalClearance() ? "Yes" : "No",
                    donor.getLastDonationDate() != null ? donor.getLastDonationDate().toString() : "Never"
                };
                availableDonorsModel.addRow(row);
            }
        }
    }
    
    /**
     * Submit new request
     */
    private void submitNewRequest(JComboBox<String> bloodGroupCombo, JComboBox<String> organTypeCombo, 
                                JComboBox<String> urgencyCombo, JComboBox<String> hospitalCombo, 
                                JTextArea medicalConditionArea, List<com.blooddonation.model.Hospital> hospitals) {
        String bloodGroupStr = (String) bloodGroupCombo.getSelectedItem();
        String organTypeStr = (String) organTypeCombo.getSelectedItem();
        String urgencyStr = (String) urgencyCombo.getSelectedItem();
        String hospitalName = (String) hospitalCombo.getSelectedItem();
        String medicalCondition = medicalConditionArea.getText().trim();
        
        if (medicalCondition.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please describe your medical condition.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Find hospital ID
        int hospitalId = 0;
        for (com.blooddonation.model.Hospital hospital : hospitals) {
            if (hospital.getName().equals(hospitalName)) {
                hospitalId = hospital.getId();
                break;
            }
        }
        
        // Convert strings to enums
        Receiver.BloodGroup bloodGroup = Receiver.BloodGroup.valueOf(bloodGroupStr.replace("+", "_POSITIVE").replace("-", "_NEGATIVE"));
        Receiver.OrganType organType = Receiver.OrganType.valueOf(organTypeStr.toUpperCase().replace(" ", "_"));
        Receiver.UrgencyLevel urgency = Receiver.UrgencyLevel.valueOf(urgencyStr.toUpperCase());
        
        // Create receiver object
        Receiver receiver = new Receiver(currentUser.getId(), bloodGroup, organType, urgency, medicalCondition, hospitalId);
        
        // Save to database
        if (receiverDAO.createReceiver(receiver)) {
            JOptionPane.showMessageDialog(this, "Request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            bloodGroupCombo.setSelectedIndex(0);
            organTypeCombo.setSelectedIndex(0);
            urgencyCombo.setSelectedIndex(1); // Default to Medium
            hospitalCombo.setSelectedIndex(0);
            medicalConditionArea.setText("");
            
            // Refresh my requests table
            loadMyRequests();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to submit request. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
