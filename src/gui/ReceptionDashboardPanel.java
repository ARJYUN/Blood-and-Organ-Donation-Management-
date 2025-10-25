package gui;

import database.DonorDAO;
import database.CharityDAO;

import javax.swing.*;
import java.awt.*;

/**
 * Reception Dashboard Panel - Statistics and overview
 */
public class ReceptionDashboardPanel extends JPanel {
    private DonorDAO donorDAO;
    private CharityDAO charityDAO;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    
    public ReceptionDashboardPanel() {
        donorDAO = new DonorDAO();
        charityDAO = new CharityDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        
        JLabel headerLabel = new JLabel("Dashboard - Overview");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 2, 30, 30));
        contentPanel.setBackground(WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Statistics cards
        int totalDonors = donorDAO.getDonorCount();
        double totalDonations = charityDAO.getTotalDonationsAmount();
        int activeRequests = charityDAO.getActiveCharityRequests().size();
        
        contentPanel.add(createStatCard("Total Donors", String.valueOf(totalDonors), PRIMARY_RED));
        contentPanel.add(createStatCard("Active Charity Requests", String.valueOf(activeRequests), DARK_RED));
        contentPanel.add(createStatCard("Total Donations", "₹" + String.format("%.2f", totalDonations), PRIMARY_RED));
        contentPanel.add(createStatCard("System Status", "Active", new Color(34, 139, 34)));
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
}
