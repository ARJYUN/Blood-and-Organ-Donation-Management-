package com.blooddonation.view;

import com.blooddonation.model.User;
import com.blooddonation.database.UserDAO;
import com.blooddonation.database.DonorDAO;
import com.blooddonation.database.ReceiverDAO;
import com.blooddonation.database.HospitalDAO;
import com.blooddonation.controller.ExportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Admin Dashboard
 * Provides administrative functions for managing the system
 */
public class AdminDashboard extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;
    private UserDAO userDAO;
    private DonorDAO donorDAO;
    private ReceiverDAO receiverDAO;
    private HospitalDAO hospitalDAO;
    private ExportController exportController;
    
    public AdminDashboard(User user) {
        this.currentUser = user;
        this.userDAO = new UserDAO();
        this.donorDAO = new DonorDAO();
        this.receiverDAO = new ReceiverDAO();
        this.hospitalDAO = new HospitalDAO();
        this.exportController = new ExportController();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Admin Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    /**
     * Initialize dashboard components
     */
    private void initializeComponents() {
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("Donors", createDonorsPanel());
        tabbedPane.addTab("Receivers", createReceiversPanel());
        tabbedPane.addTab("Hospitals", createHospitalsPanel());
        tabbedPane.addTab("Statistics", createStatisticsPanel());
        tabbedPane.addTab("Export", createExportPanel());
    }
    
    /**
     * Setup dashboard layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + " (Admin)");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
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
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Add any additional event handlers here
    }
    
    /**
     * Create dashboard overview panel
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        // Get statistics
        int[] donorStats = donorDAO.getDonorStatistics();
        int[] receiverStats = receiverDAO.getReceiverStatistics();
        int hospitalCount = hospitalDAO.getHospitalCount();
        int totalUsers = userDAO.getAllUsers().size();
        
        // Create stat cards
        statsPanel.add(createStatCard("Total Users", String.valueOf(totalUsers), Color.BLUE));
        statsPanel.add(createStatCard("Total Donors", String.valueOf(donorStats[0]), Color.GREEN));
        statsPanel.add(createStatCard("Total Receivers", String.valueOf(receiverStats[0]), Color.ORANGE));
        statsPanel.add(createStatCard("Total Hospitals", String.valueOf(hospitalCount), Color.RED));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Recent activity panel
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBorder(BorderFactory.createTitledBorder("Recent Activity"));
        
        JTextArea activityText = new JTextArea(10, 50);
        activityText.setEditable(false);
        activityText.setText("System Overview:\n" +
                "• " + donorStats[1] + " donors are currently available\n" +
                "• " + receiverStats[1] + " requests are pending approval\n" +
                "• " + receiverStats[2] + " requests have been approved\n" +
                "• " + hospitalCount + " hospitals are registered in the system");
        
        JScrollPane scrollPane = new JScrollPane(activityText);
        activityPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(activityPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create a statistics card
     */
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    /**
     * Create users management panel
     */
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Users table
        String[] columns = {"ID", "Name", "Email", "Role", "Location", "Phone", "Created"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable usersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        
        // Load users data
        loadUsersData(model);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create donors management panel
     */
    private JPanel createDonorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Donors table
        String[] columns = {"ID", "User ID", "Blood Group", "Organ Type", "Status", "Medical Clearance", "Last Donation"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable donorsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(donorsTable);
        
        // Load donors data
        loadDonorsData(model);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create receivers management panel
     */
    private JPanel createReceiversPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Receivers table
        String[] columns = {"ID", "User ID", "Required Blood", "Required Organ", "Urgency", "Status", "Hospital ID", "Request Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable receiversTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(receiversTable);
        
        // Load receivers data
        loadReceiversData(model);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create hospitals management panel
     */
    private JPanel createHospitalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Hospitals table
        String[] columns = {"ID", "Name", "Address", "Contact", "Email", "Specialization"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable hospitalsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(hospitalsTable);
        
        // Load hospitals data
        loadHospitalsData(model);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create statistics panel
     */
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea statsText = new JTextArea(20, 50);
        statsText.setEditable(false);
        statsText.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Get and display statistics
        int[] donorStats = donorDAO.getDonorStatistics();
        int[] receiverStats = receiverDAO.getReceiverStatistics();
        int hospitalCount = hospitalDAO.getHospitalCount();
        int totalUsers = userDAO.getAllUsers().size();
        
        statsText.setText("SYSTEM STATISTICS\n" +
                "==================\n\n" +
                "USERS:\n" +
                "• Total Users: " + totalUsers + "\n" +
                "• Admin Users: " + userDAO.getUsersByRole(User.UserRole.ADMIN).size() + "\n" +
                "• Donor Users: " + userDAO.getUsersByRole(User.UserRole.DONOR).size() + "\n" +
                "• Receiver Users: " + userDAO.getUsersByRole(User.UserRole.RECEIVER).size() + "\n" +
                "• Charity Users: " + userDAO.getUsersByRole(User.UserRole.CHARITY).size() + "\n\n" +
                "DONORS:\n" +
                "• Total Donors: " + donorStats[0] + "\n" +
                "• Available Donors: " + donorStats[1] + "\n" +
                "• Unavailable Donors: " + donorStats[2] + "\n\n" +
                "RECEIVERS:\n" +
                "• Total Receivers: " + receiverStats[0] + "\n" +
                "• Pending Requests: " + receiverStats[1] + "\n" +
                "• Approved Requests: " + receiverStats[2] + "\n" +
                "• Rejected Requests: " + receiverStats[3] + "\n" +
                "• Fulfilled Requests: " + receiverStats[4] + "\n\n" +
                "HOSPITALS:\n" +
                "• Total Hospitals: " + hospitalCount + "\n");
        
        JScrollPane scrollPane = new JScrollPane(statsText);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Load users data into table model
     */
    private void loadUsersData(DefaultTableModel model) {
        model.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            Object[] row = {
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getDisplayName(),
                user.getLocation(),
                user.getPhone(),
                user.getCreatedAt() != null ? user.getCreatedAt().toLocalDate().toString() : "N/A"
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load donors data into table model
     */
    private void loadDonorsData(DefaultTableModel model) {
        model.setRowCount(0);
        List<com.blooddonation.model.Donor> donors = donorDAO.getAllDonors();
        for (com.blooddonation.model.Donor donor : donors) {
            Object[] row = {
                donor.getId(),
                donor.getUserId(),
                donor.getBloodGroup().getDisplayName(),
                donor.getOrganType().getDisplayName(),
                donor.getAvailabilityStatus().getDisplayName(),
                donor.isMedicalClearance() ? "Yes" : "No",
                donor.getLastDonationDate() != null ? donor.getLastDonationDate().toString() : "Never"
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load receivers data into table model
     */
    private void loadReceiversData(DefaultTableModel model) {
        model.setRowCount(0);
        List<com.blooddonation.model.Receiver> receivers = receiverDAO.getAllReceivers();
        for (com.blooddonation.model.Receiver receiver : receivers) {
            Object[] row = {
                receiver.getId(),
                receiver.getUserId(),
                receiver.getRequiredBloodGroup().getDisplayName(),
                receiver.getRequiredOrgan().getDisplayName(),
                receiver.getUrgencyLevel().getDisplayName(),
                receiver.getStatus().getDisplayName(),
                receiver.getHospitalId(),
                receiver.getRequestDate().toString()
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load hospitals data into table model
     */
    private void loadHospitalsData(DefaultTableModel model) {
        model.setRowCount(0);
        List<com.blooddonation.model.Hospital> hospitals = hospitalDAO.getAllHospitals();
        for (com.blooddonation.model.Hospital hospital : hospitals) {
            Object[] row = {
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getContactNumber(),
                hospital.getEmail(),
                hospital.getSpecialization()
            };
            model.addRow(row);
        }
    }
    
    /**
     * Create export panel
     */
    private JPanel createExportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Export options
        JLabel titleLabel = new JLabel("Data Export Options", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        
        JButton exportDonorsBtn = new JButton("Export Donors Data (CSV)");
        exportDonorsBtn.setBackground(new Color(34, 139, 34));
        exportDonorsBtn.setForeground(Color.WHITE);
        exportDonorsBtn.setPreferredSize(new Dimension(200, 40));
        
        JButton exportReceiversBtn = new JButton("Export Receivers Data (CSV)");
        exportReceiversBtn.setBackground(new Color(255, 140, 0));
        exportReceiversBtn.setForeground(Color.WHITE);
        exportReceiversBtn.setPreferredSize(new Dimension(200, 40));
        
        JButton exportStatsBtn = new JButton("Export Statistics Report (PDF)");
        exportStatsBtn.setBackground(new Color(70, 130, 180));
        exportStatsBtn.setForeground(Color.WHITE);
        exportStatsBtn.setPreferredSize(new Dimension(200, 40));
        
        buttonPanel.add(exportDonorsBtn);
        buttonPanel.add(exportReceiversBtn);
        buttonPanel.add(exportStatsBtn);
        
        // Event handlers
        exportDonorsBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("donors_export.csv"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (exportController.exportDonorsToCSV(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(this, "Donors data exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to export donors data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        exportReceiversBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("receivers_export.csv"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (exportController.exportReceiversToCSV(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(this, "Receivers data exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to export receivers data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        exportStatsBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("statistics_report.pdf"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (exportController.exportStatisticsToPDF(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(this, "Statistics report exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to export statistics report.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
}
