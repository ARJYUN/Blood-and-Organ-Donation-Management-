package gui;

import database.CharityDAO;
import models.CharityRequest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Charity Panel - View and donate to charity requests
 */
public class CharityPanel extends JPanel {
    private CharityDAO charityDAO;
    private JPanel requestsPanel;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    
    public CharityPanel() {
        charityDAO = new CharityDAO();
        initializeUI();
        loadCharityRequests();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel headerLabel = new JLabel("Active Charity Requests");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(PRIMARY_RED);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadCharityRequests());
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        // Requests Panel
        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(WHITE);
        requestsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadCharityRequests() {
        requestsPanel.removeAll();
        List<CharityRequest> requests = charityDAO.getActiveCharityRequests();
        
        if (requests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("No active charity requests at the moment");
            noRequestsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noRequestsLabel.setForeground(Color.GRAY);
            requestsPanel.add(noRequestsLabel);
        } else {
            for (CharityRequest request : requests) {
                requestsPanel.add(createRequestCard(request));
                requestsPanel.add(Box.createVerticalStrut(15));
            }
        }
        
        requestsPanel.revalidate();
        requestsPanel.repaint();
    }
    
    private JPanel createRequestCard(CharityRequest request) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(900, 200));
        
        // Left panel - Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(WHITE);
        
        JLabel titleLabel = new JLabel(request.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(DARK_RED);
        
        JLabel descLabel = new JLabel("<html>" + request.getDescription() + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel requesterLabel = new JLabel("By: " + request.getRequesterName());
        requesterLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        requesterLabel.setForeground(Color.GRAY);
        
        JLabel typeLabel = new JLabel("Type: " + request.getType());
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(requesterLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(typeLabel);
        
        // Right panel - Progress and Donate button
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(WHITE);
        rightPanel.setPreferredSize(new Dimension(250, 150));
        
        JLabel goalLabel = new JLabel("Goal: ₹" + String.format("%.2f", request.getGoalAmount()));
        goalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel raisedLabel = new JLabel("Raised: ₹" + String.format("%.2f", request.getRaisedAmount()));
        raisedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        raisedLabel.setForeground(new Color(34, 139, 34));
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) request.getProgressPercentage());
        progressBar.setStringPainted(true);
        progressBar.setString(String.format("%.1f%%", request.getProgressPercentage()));
        progressBar.setForeground(PRIMARY_RED);
        progressBar.setPreferredSize(new Dimension(230, 25));
        
        JButton donateButton = new JButton("DONATE NOW");
        donateButton.setFont(new Font("Arial", Font.BOLD, 12));
        donateButton.setBackground(PRIMARY_RED);
        donateButton.setForeground(WHITE);
        donateButton.setFocusPainted(false);
        donateButton.setBorderPainted(false);
        donateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        donateButton.setMaximumSize(new Dimension(200, 35));
        donateButton.addActionListener(e -> openDonationDialog(request));
        
        rightPanel.add(goalLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(raisedLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(progressBar);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(donateButton);
        
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private void openDonationDialog(CharityRequest request) {
        new DonationDialog(this, request).setVisible(true);
    }
    
    public void refreshRequests() {
        loadCharityRequests();
    }
}
