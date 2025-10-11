package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard view for administrators
 */
public class AdminDashboardView extends JFrame {
    private final AuthController authController;
    
    public AdminDashboardView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Blood & Organ Donation Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Create sidebar panel
        JPanel sidebarPanel = createSidebarPanel();
        
        // Create content panel
        JPanel contentPanel = createContentPanel();
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeUtil.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create welcome message
        String username = authController.getCurrentUser().getUsername();
        JLabel welcomeLabel = new JLabel("Admin Dashboard - Welcome, " + username + "!");
        welcomeLabel.setFont(ThemeUtil.HEADER_FONT);
        welcomeLabel.setForeground(Color.WHITE);
        
        // Create logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(ThemeUtil.SMALL_FONT);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(52, 73, 94));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        
        // Create menu buttons
        String[] menuItems = {
            "Dashboard", "Manage Users", "Manage Donors", "Blood Donations", 
            "Organ Donations", "Charity Donations", "Reports", "Settings"
        };
        
        for (String menuItem : menuItems) {
            JButton menuButton = createMenuButton(menuItem);
            sidebarPanel.add(menuButton);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        return sidebarPanel;
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ThemeUtil.REGULAR_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(230, 40));
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMenuSelection(text);
            }
        });
        
        return button;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create welcome content
        JPanel welcomePanel = new JPanel(new BorderLayout(0, 20));
        welcomePanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        JLabel titleLabel = ThemeUtil.createTitleLabel("Admin Dashboard");
        
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        statsPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        // Add stat cards
        statsPanel.add(createStatCard("Total Users", "0", ThemeUtil.PRIMARY_COLOR));
        statsPanel.add(createStatCard("Total Donors", "0", ThemeUtil.SECONDARY_COLOR));
        statsPanel.add(createStatCard("Blood Donations", "0", new Color(231, 76, 60)));
        statsPanel.add(createStatCard("Organ Donations", "0", new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Charity Donations", "$0", ThemeUtil.ACCENT_COLOR));
        statsPanel.add(createStatCard("Active Schedules", "0", new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Pending Reviews", "0", new Color(241, 196, 15)));
        statsPanel.add(createStatCard("System Health", "Good", new Color(46, 204, 113)));
        
        // Add information panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoTitle = ThemeUtil.createHeaderLabel("System Overview");
        
        JTextArea infoText = new JTextArea(
            "Welcome to the Admin Dashboard! From here you can:\n\n" +
            "• Manage user accounts and permissions\n" +
            "• View and manage donor information\n" +
            "• Track all types of donations (blood, organ, charity)\n" +
            "• Generate reports and analytics\n" +
            "• Configure system settings\n" +
            "• Monitor system health and performance\n\n" +
            "Use the sidebar menu to navigate to different management sections."
        );
        infoText.setFont(ThemeUtil.REGULAR_FONT);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setEditable(false);
        infoText.setBackground(Color.WHITE);
        
        infoPanel.add(infoTitle, BorderLayout.NORTH);
        infoPanel.add(infoText, BorderLayout.CENTER);
        
        welcomePanel.add(titleLabel, BorderLayout.NORTH);
        welcomePanel.add(statsPanel, BorderLayout.CENTER);
        welcomePanel.add(infoPanel, BorderLayout.SOUTH);
        
        contentPanel.add(welcomePanel, BorderLayout.CENTER);
        
        return contentPanel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ThemeUtil.SMALL_FONT);
        titleLabel.setForeground(ThemeUtil.TEXT_COLOR);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(color);
        
        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        
        return cardPanel;
    }
    
    private void handleMenuSelection(String menuItem) {
        switch (menuItem) {
            case "Manage Users":
                openUserManagementView();
                break;
            case "Manage Donors":
                openDonorManagementView();
                break;
            case "Blood Donations":
                openBloodDonationManagementView();
                break;
            case "Organ Donations":
                openOrganDonationManagementView();
                break;
            case "Charity Donations":
                openCharityDonationManagementView();
                break;
            case "Reports":
                openReportsView();
                break;
            case "Settings":
                openSettingsView();
                break;
            default:
                // Dashboard or unknown menu item, do nothing
                break;
        }
    }
    
    private void openUserManagementView() {
        JOptionPane.showMessageDialog(this, "User Management module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openDonorManagementView() {
        JOptionPane.showMessageDialog(this, "Donor Management module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openBloodDonationManagementView() {
        JOptionPane.showMessageDialog(this, "Blood Donation Management module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openOrganDonationManagementView() {
        JOptionPane.showMessageDialog(this, "Organ Donation Management module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openCharityDonationManagementView() {
        JOptionPane.showMessageDialog(this, "Charity Donation Management module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openReportsView() {
        JOptionPane.showMessageDialog(this, "Reports module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openSettingsView() {
        JOptionPane.showMessageDialog(this, "Settings module will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleLogout() {
        authController.logout();
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        dispose();
    }
}
