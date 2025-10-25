package gui;

import utils.SessionManager;
import models.User;

import javax.swing.*;
import java.awt.*;

/**
 * Main Dashboard - Central navigation hub
 */
public class MainDashboard extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    private static final Color BLACK = Color.BLACK;

    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    
    public MainDashboard() {
        initializeUI();
    }
    
    private void initializeUI() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String role = currentUser.getRole();
        
        setTitle("Blood & Organ Donation Management System - Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(PRIMARY_RED);
        topBar.setPreferredSize(new Dimension(1200, 70));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Blood & Organ Donation Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(PRIMARY_RED);
        
        JLabel userLabel = new JLabel("Welcome, " + currentUser.getUsername() + " (" + role + ")");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(WHITE);
        logoutButton.setForeground(BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> logout());
        
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(20));
        userPanel.add(logoutButton);
        
        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(userPanel, BorderLayout.EAST);
        
        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(LIGHT_GRAY);
        sidebar.setPreferredSize(new Dimension(250, 730));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(WHITE);
        
        // Add navigation buttons based on role
        if ("DONOR".equals(role)) {
            addNavigationButton(sidebar, "My Profile", "profile");
            addNavigationButton(sidebar, "Update Information", "update");
            addNavigationButton(sidebar, "Charity Requests", "charity");
            
            contentPanel.add(new DonorProfilePanel(), "profile");
            contentPanel.add(new DonorUpdatePanel(), "update");
            contentPanel.add(new CharityPanel(), "charity");
            
        } else if ("RECEPTIONIST".equals(role)) {
            addNavigationButton(sidebar, "Dashboard", "dashboard");
            addNavigationButton(sidebar, "View All Donors", "viewDonors");
            addNavigationButton(sidebar, "Search Donors", "searchDonors");
            addNavigationButton(sidebar, "Charity Management", "charityManage");
            
            contentPanel.add(new ReceptionDashboardPanel(), "dashboard");
            contentPanel.add(new ViewDonorsPanel(), "viewDonors");
            contentPanel.add(new SearchDonorsPanel(), "searchDonors");
            contentPanel.add(new CharityManagementPanel(), "charityManage");
            
        } else if ("RECIPIENT".equals(role)) {
            addNavigationButton(sidebar, "Find Donors", "findDonors");
            addNavigationButton(sidebar, "Charity Requests", "charity");
            
            contentPanel.add(new RecipientDashboardPanel(), "findDonors");
            contentPanel.add(new CharityPanel(), "charity");
        }
        
        // Add common charity panel
        sidebar.add(Box.createVerticalGlue());
        
        // Assemble
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void addNavigationButton(JPanel sidebar, String text, String cardName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        // button.setBackground(WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(230, 45));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        button.addActionListener(e -> {
            cardLayout.show(contentPanel, cardName);
            // Reset all buttons
            for (Component comp : sidebar.getComponents()) {
                if (comp instanceof JButton) {
                    comp.setForeground(PRIMARY_RED);
                }
            }
            button.setBackground(PRIMARY_RED);
            button.setForeground(PRIMARY_RED);
        });
        
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
        
        // Set first button as active
        if (sidebar.getComponentCount() == 2) {
            button.setBackground(PRIMARY_RED);
            button.setForeground(PRIMARY_RED);
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
