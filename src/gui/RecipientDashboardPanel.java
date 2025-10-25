package gui;

import database.DonorDAO;
import database.RecipientDAO;
import models.Donor;
import models.Recipient;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Recipient Dashboard Panel - View matching donors
 */
public class RecipientDashboardPanel extends JPanel {
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = Color.BLACK;
    
    private Recipient recipient;
    private JTable donorTable;
    private DefaultTableModel tableModel;
    private JLabel profileLabel;
    private JLabel matchCountLabel;
    
    public RecipientDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Load recipient data
        loadRecipientData();
        
        initComponents();
        loadMatchingDonors();
    }
    
    private void loadRecipientData() {
        int userId = SessionManager.getInstance().getCurrentUserId();
        recipient = RecipientDAO.getRecipientByUserId(userId);
    }
    
    private void initComponents() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("RECIPIENT DASHBOARD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(Color.WHITE);
        
        profileLabel = new JLabel();
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        profileLabel.setForeground(Color.RED);
        profileLabel.setBackground(Color.WHITE);
        updateProfileLabel();
        
        JPanel headerTextPanel = new JPanel(new BorderLayout());
        headerTextPanel.setBackground(PRIMARY_RED);
        headerTextPanel.add(titleLabel, BorderLayout.NORTH);
        headerTextPanel.add(profileLabel, BorderLayout.CENTER);
        
        headerPanel.add(headerTextPanel, BorderLayout.WEST);
        
        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel infoLabel = new JLabel("Showing donors matching your requirements:");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setForeground(DARK_RED);
        
        matchCountLabel = new JLabel("0 matches found");
        matchCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        matchCountLabel.setForeground(Color.GRAY);
        
        infoPanel.add(infoLabel);
        infoPanel.add(matchCountLabel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setForeground(PRIMARY_RED);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadMatchingDonors());
        
        JButton viewProfileButton = new JButton("MY PROFILE");
        viewProfileButton.setForeground(PRIMARY_RED);
        viewProfileButton.setFont(new Font("Arial", Font.BOLD, 10));
        viewProfileButton.setFocusPainted(false);
        viewProfileButton.addActionListener(e -> showRecipientProfile());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewProfileButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Table
        String[] columns = {"Donor ID", "Name", "Age", "Gender", "Blood Group", "Organ", "Contact", "Location"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorTable = new JTable(tableModel);
        donorTable.setFont(new Font("Arial", Font.PLAIN, 12));
        donorTable.setRowHeight(25);
        donorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        donorTable.getTableHeader().setBackground(PRIMARY_RED);
        donorTable.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(donorTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Add components
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void updateProfileLabel() {
        if (recipient != null) {
            String organInfo = (recipient.getOrganNeeded() != null && !recipient.getOrganNeeded().isEmpty()) 
                ? " | Organ: " + recipient.getOrganNeeded() : "";
            profileLabel.setText(String.format("Welcome, %s | Need: %s%s | Urgency: %s | Location: %s",
                recipient.getName(),
                recipient.getBloodGroupNeeded(),
                organInfo,
                recipient.getUrgencyLevel(),
                recipient.getLocation()));
                profileLabel.setForeground(Color.WHITE);
        }
    }
    
    private void loadMatchingDonors() {
        if (recipient == null) {
            JOptionPane.showMessageDialog(this, "Unable to load recipient data!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Search for matching donors by blood group
        DonorDAO donorDAO = new DonorDAO();
        List<Donor> matchingDonors = donorDAO.searchByBloodGroup(recipient.getBloodGroupNeeded());
        
        // If organ is specified, filter by organ as well
        if (recipient.getOrganNeeded() != null && !recipient.getOrganNeeded().isEmpty()) {
            List<Donor> organDonors = donorDAO.searchByOrgan(recipient.getOrganNeeded());
            // Keep only donors that match both blood group and organ
            matchingDonors.retainAll(organDonors);
        }
        
        // Populate table
        for (Donor donor : matchingDonors) {
            Object[] row = {
                donor.getDonorId(),
                donor.getName(),
                donor.getAge(),
                donor.getGender(),
                donor.getBloodGroup(),
                donor.getOrgan() != null ? donor.getOrgan() : "N/A",
                donor.getContact(),
                donor.getLocation()
            };
            tableModel.addRow(row);
        }
        
        // Update match count
        matchCountLabel.setText(matchingDonors.size() + " match(es) found");
        
        if (matchingDonors.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No matching donors found for your requirements.\nPlease check back later.", 
                "No Matches", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showRecipientProfile() {
        if (recipient == null) {
            JOptionPane.showMessageDialog(this, "Unable to load recipient data!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String organInfo = (recipient.getOrganNeeded() != null && !recipient.getOrganNeeded().isEmpty()) 
            ? "\nOrgan Needed: " + recipient.getOrganNeeded() : "";
        String medicalInfo = (recipient.getMedicalCondition() != null && !recipient.getMedicalCondition().isEmpty())
            ? "\nMedical Condition: " + recipient.getMedicalCondition() : "";
        
        String profileInfo = String.format(
            "RECIPIENT PROFILE\n\n" +
            "Name: %s\n" +
            "Age: %d\n" +
            "Gender: %s\n" +
            "Blood Group Needed: %s%s\n" +
            "Contact: %s\n" +
            "Location: %s\n" +
            "Urgency Level: %s%s\n" +
            "Registration Date: %s",
            recipient.getName(),
            recipient.getAge(),
            recipient.getGender(),
            recipient.getBloodGroupNeeded(),
            organInfo,
            recipient.getContact(),
            recipient.getLocation(),
            recipient.getUrgencyLevel(),
            medicalInfo,
            "Available in database"
        );
        
        JOptionPane.showMessageDialog(this, profileInfo, 
                                    "My Profile", JOptionPane.INFORMATION_MESSAGE);
    }
}
