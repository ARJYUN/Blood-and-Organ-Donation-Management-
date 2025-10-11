package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Login view for user authentication
 */
public class LoginView extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(LoginView.class.getName());
    
    private final AuthController authController;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    public LoginView() {
        authController = new AuthController();
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Blood & Organ Donation Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        // Create logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        JLabel titleLabel = ThemeUtil.createTitleLabel("Blood & Organ Donation Management System");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitleLabel = ThemeUtil.createHeaderLabel("Saving Lives Through Donation");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        logoPanel.add(titleLabel, BorderLayout.CENTER);
        logoPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Create login form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username field
        JLabel usernameLabel = ThemeUtil.createStyledLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setFont(ThemeUtil.REGULAR_FONT);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);
        
        // Password field
        JLabel passwordLabel = ThemeUtil.createStyledLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setFont(ThemeUtil.REGULAR_FONT);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        loginButton = ThemeUtil.createStyledButton("Login");
        registerButton = ThemeUtil.createStyledButton("Register");
        
        // Style the register button differently
        registerButton.setBackground(ThemeUtil.SECONDARY_COLOR);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        // Add components to main panel
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationView();
            }
        });
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = authController.login(username, password);
        
        if (success) {
            LOGGER.info("Login successful for user: " + username);
            
            // Open appropriate dashboard based on user role
            if (authController.isAdmin()) {
                openAdminDashboard();
            } else {
                openUserDashboard();
            }
            
            // Close login window
            dispose();
        } else {
            LOGGER.warning("Login failed for user: " + username);
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openRegistrationView() {
        RegistrationView registrationView = new RegistrationView();
        registrationView.setVisible(true);
        dispose();
    }
    
    private void openAdminDashboard() {
        AdminDashboardView adminDashboard = new AdminDashboardView(authController);
        adminDashboard.setVisible(true);
    }
    
    private void openUserDashboard() {
        UserDashboardView userDashboard = new UserDashboardView(authController);
        userDashboard.setVisible(true);
    }
}