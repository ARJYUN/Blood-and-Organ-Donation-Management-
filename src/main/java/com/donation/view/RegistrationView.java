package com.donation.view;

import com.donation.controller.AuthController;
import com.donation.model.User;
import com.donation.util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Registration view for new user signup
 */
public class RegistrationView extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(RegistrationView.class.getName());
    
    private final AuthController authController;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton backButton;
    
    public RegistrationView() {
        authController = new AuthController();
        initializeUI();
    }
    
    private void initializeUI() {
        // Set up the frame
        setTitle("Blood & Organ Donation Management System - Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        JLabel titleLabel = ThemeUtil.createTitleLabel("Create New Account");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitleLabel = ThemeUtil.createHeaderLabel("Join our donation management system");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Create registration form panel
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
        
        // Confirm password field
        JLabel confirmPasswordLabel = ThemeUtil.createStyledLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(ThemeUtil.REGULAR_FONT);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordField, gbc);
        
        // Email field
        JLabel emailLabel = ThemeUtil.createStyledLabel("Email:");
        emailField = new JTextField(20);
        emailField.setFont(ThemeUtil.REGULAR_FONT);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(emailField, gbc);
        
        // Role selection
        JLabel roleLabel = ThemeUtil.createStyledLabel("Role:");
        String[] roles = {"USER", "ADMIN"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(ThemeUtil.REGULAR_FONT);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(roleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(roleComboBox, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(ThemeUtil.BACKGROUND_COLOR);
        
        registerButton = ThemeUtil.createStyledButton("Register");
        backButton = ThemeUtil.createStyledButton("Back to Login");
        
        // Style the back button differently
        backButton.setBackground(ThemeUtil.SECONDARY_COLOR);
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToLogin();
            }
        });
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        
        // Validate input
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Attempt to register the user
        User newUser = authController.registerUser(username, password, email, role);
        
        if (newUser != null) {
            LOGGER.info("User registered successfully: " + username);
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please log in.",
                    "Registration Success",
                    JOptionPane.INFORMATION_MESSAGE);
            backToLogin();
        } else {
            LOGGER.warning("Registration failed for user: " + username);
            JOptionPane.showMessageDialog(this,
                    "Registration failed. Username may already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void backToLogin() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        dispose();
    }
}