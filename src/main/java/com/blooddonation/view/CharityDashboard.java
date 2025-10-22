package com.blooddonation.view;

import com.blooddonation.model.User;
import com.blooddonation.model.Charity;
import com.blooddonation.database.CharityDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Charity Dashboard
 * Provides interface for charity organizations to manage events
 */
public class CharityDashboard extends JFrame {
    private User currentUser;
    private CharityDAO charityDAO;
    private JTable eventsTable;
    private DefaultTableModel eventsModel;
    
    public CharityDashboard(User user) {
        this.currentUser = user;
        this.charityDAO = new CharityDAO();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Charity Dashboard");
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
        headerPanel.setBackground(new Color(128, 0, 128));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + " (Charity)");
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
        
        // My Events tab
        tabbedPane.addTab("My Events", createMyEventsPanel());
        
        // New Event tab
        tabbedPane.addTab("New Event", createNewEventPanel());
        
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
     * Create my events panel
     */
    private JPanel createMyEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Events table
        String[] columns = {"ID", "Organization", "Event Name", "Event Date", "Location", "Status", "Contact Info"};
        eventsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventsTable = new JTable(eventsModel);
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        
        // Load my events
        loadMyEvents();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create new event panel
     */
    private JPanel createNewEventPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Organization Name
        JLabel orgNameLabel = new JLabel("Organization Name:");
        JTextField orgNameField = new JTextField(20);
        orgNameField.setPreferredSize(new Dimension(200, 25));
        
        // Event Name
        JLabel eventNameLabel = new JLabel("Event Name:");
        JTextField eventNameField = new JTextField(20);
        eventNameField.setPreferredSize(new Dimension(200, 25));
        
        // Event Date
        JLabel eventDateLabel = new JLabel("Event Date:");
        JTextField eventDateField = new JTextField(20);
        eventDateField.setPreferredSize(new Dimension(200, 25));
        eventDateField.setText(LocalDate.now().toString());
        
        // Event Location
        JLabel eventLocationLabel = new JLabel("Event Location:");
        JTextField eventLocationField = new JTextField(20);
        eventLocationField.setPreferredSize(new Dimension(200, 25));
        
        // Contact Info
        JLabel contactInfoLabel = new JLabel("Contact Info:");
        JTextField contactInfoField = new JTextField(20);
        contactInfoField.setPreferredSize(new Dimension(200, 25));
        
        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea(3, 30);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Create Event button
        JButton createEventButton = new JButton("Create Event");
        createEventButton.setBackground(new Color(128, 0, 128));
        createEventButton.setForeground(Color.WHITE);
        createEventButton.setPreferredSize(new Dimension(120, 30));
        
        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(orgNameLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(orgNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(eventNameLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(eventNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(eventDateLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(eventDateField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(eventLocationLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(eventLocationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        panel.add(contactInfoLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(contactInfoField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(descriptionArea, gbc);
        
        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(createEventButton, gbc);
        
        // Create event button action
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewEvent(orgNameField, eventNameField, eventDateField, eventLocationField, contactInfoField, descriptionArea);
            }
        });
        
        return panel;
    }
    
    /**
     * Load my events data
     */
    private void loadMyEvents() {
        eventsModel.setRowCount(0);
        List<Charity> events = charityDAO.findByUserId(currentUser.getId());
        for (Charity event : events) {
            Object[] row = {
                event.getId(),
                event.getOrganizationName(),
                event.getEventName(),
                event.getEventDate().toString(),
                event.getEventLocation(),
                event.getStatus().getDisplayName(),
                event.getContactInfo()
            };
            eventsModel.addRow(row);
        }
    }
    
    /**
     * Create new event
     */
    private void createNewEvent(JTextField orgNameField, JTextField eventNameField, JTextField eventDateField, 
                               JTextField eventLocationField, JTextField contactInfoField, JTextArea descriptionArea) {
        String orgName = orgNameField.getText().trim();
        String eventName = eventNameField.getText().trim();
        String eventDateStr = eventDateField.getText().trim();
        String eventLocation = eventLocationField.getText().trim();
        String contactInfo = contactInfoField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        // Validate input
        if (orgName.isEmpty() || eventName.isEmpty() || eventDateStr.isEmpty() || eventLocation.isEmpty() || contactInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            LocalDate eventDate = LocalDate.parse(eventDateStr);
            
            // Create charity object
            Charity charity = new Charity(currentUser.getId(), orgName, eventName, eventDate, eventLocation, contactInfo, description);
            
            // Save to database
            if (charityDAO.createCharity(charity)) {
                JOptionPane.showMessageDialog(this, "Event created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear form
                orgNameField.setText("");
                eventNameField.setText("");
                eventDateField.setText(LocalDate.now().toString());
                eventLocationField.setText("");
                contactInfoField.setText("");
                descriptionArea.setText("");
                
                // Refresh events table
                loadMyEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create event. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
