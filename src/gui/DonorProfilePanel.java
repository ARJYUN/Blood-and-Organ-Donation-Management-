package gui;

import database.DonorDAO;
import models.Donor;
import utils.SessionManager;

import javax.swing.*;
import java.awt.*;

/**
 * Donor Profile Panel - View donor information
 */
public class DonorProfilePanel extends JPanel {
    private DonorDAO donorDAO;
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public DonorProfilePanel() {
        donorDAO = new DonorDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        
        JLabel headerLabel = new JLabel("My Profile");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Load donor data
        int userId = SessionManager.getInstance().getCurrentUserId();
        Donor donor = donorDAO.getDonorByUserId(userId);
        
        if (donor != null) {
            addInfoRow(contentPanel, "Donor ID:", String.valueOf(donor.getDonorId()));
            addInfoRow(contentPanel, "Full Name:", donor.getName());
            addInfoRow(contentPanel, "Age:", String.valueOf(donor.getAge()));
            addInfoRow(contentPanel, "Gender:", donor.getGender());
            addInfoRow(contentPanel, "Blood Group:", donor.getBloodGroup());
            addInfoRow(contentPanel, "Organ:", donor.getOrgan());
            addInfoRow(contentPanel, "Contact:", donor.getContact());
            addInfoRow(contentPanel, "Location:", donor.getLocation());
        } else {
            JLabel errorLabel = new JLabel("Unable to load profile information");
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            errorLabel.setForeground(Color.RED);
            contentPanel.add(errorLabel);
        }
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }
    
    private void addInfoRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        rowPanel.setBackground(WHITE);
        rowPanel.setMaximumSize(new Dimension(850, 50));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 16));
        labelComponent.setPreferredSize(new Dimension(150, 30));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 16));
        
        rowPanel.add(labelComponent);
        rowPanel.add(valueComponent);
        
        panel.add(rowPanel);
    }
}
