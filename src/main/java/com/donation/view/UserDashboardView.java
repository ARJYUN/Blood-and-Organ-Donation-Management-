package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard view for regular users
 */
public class UserDashboardView extends JFrame {
    private final AuthController authController;
    
    public UserDashboardView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Blood & Organ Donation Management System - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
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
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
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
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        
        // Create menu buttons
        String[] menuItems = {
            "Dashboard", "Donate Blood", "Donate Organ", "Make Charity Donation", 
            "My Donations", "Schedule Donation", "My Profile"
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
        button.setMaximumSize(new Dimension(180, 40));
        
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
        
        JLabel titleLabel = ThemeUtil.createTitleLabel("User Dashboard");
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        // Add stat cards
        statsPanel.add(createStatCard("Blood Donations", "0", ThemeUtil.PRIMARY_COLOR));
        statsPanel.add(createStatCard("Organ Donations", "0", ThemeUtil.SECONDARY_COLOR));
        statsPanel.add(createStatCard("Charity Donations", "$0", ThemeUtil.ACCENT_COLOR));
        
        // Add information panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoTitle = ThemeUtil.createHeaderLabel("Why Donate?");
        
        JTextArea infoText = new JTextArea(
            "Blood Donation: Your donation can save up to 3 lives! Blood is needed every 2 seconds.\n\n" +
            "Organ Donation: One organ donor can save up to 8 lives and enhance the lives of up to 50 people.\n\n" +
            "Charity Donation: Your financial support helps us maintain our facilities and reach more people in need."
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
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        
        return cardPanel;
    }
    
    private void handleMenuSelection(String menuItem) {
        switch (menuItem) {
            case "Donate Blood":
                openBloodDonationView();
                break;
            case "Donate Organ":
                openOrganDonationView();
                break;
            case "Make Charity Donation":
                openCharityDonationView();
                break;
            case "My Donations":
                openMyDonationsView();
                break;
            case "Schedule Donation":
                openScheduleDonationView();
                break;
            case "My Profile":
                openProfileView();
                break;
            default:
                // Dashboard or unknown menu item, do nothing
                break;
        }
    }
    
    private void openBloodDonationView() {
        BloodDonationView bloodDonationView = new BloodDonationView(authController);
        bloodDonationView.setVisible(true);
    }
    
    private void openOrganDonationView() {
        OrganDonationView organDonationView = new OrganDonationView(authController);
        organDonationView.setVisible(true);
    }
    
    private void openCharityDonationView() {
        // Create CharityDonationView when it's implemented
        JOptionPane.showMessageDialog(this, 
            "Charity Donation module is under development and will be available soon.", 
            "Coming Soon", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openMyDonationsView() {
        // To be implemented
        JOptionPane.showMessageDialog(this, "My Donations view will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openScheduleDonationView() {
        // To be implemented
        JOptionPane.showMessageDialog(this, "Schedule Donation view will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openProfileView() {
        // To be implemented
        JOptionPane.showMessageDialog(this, "Profile view will be opened", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleLogout() {
        authController.logout();
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        dispose();
    }
}