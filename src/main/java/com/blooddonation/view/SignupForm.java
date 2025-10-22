package com.blooddonation.view;

import com.blooddonation.controller.AuthController;
import com.blooddonation.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Signup Form for user registration
 * Allows new users to register with the system
 */
public class SignupForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField locationField;
    private JTextField phoneField;
    private JComboBox<String> roleComboBox;
    private JButton signupButton;
    private JButton backButton;
    private AuthController authController;
    
    public SignupForm() {
        this.authController = new AuthController();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Sign Up");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Initialize form components
     */
    private void initializeComponents() {
        // Title label
        JLabel titleLabel = new JLabel("Create New Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Form fields
        JLabel nameLabel = new JLabel("Full Name:");
        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 25));
        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(200, 25));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 25));
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(200, 25));
        
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Donor", "Receiver", "Charity"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setPreferredSize(new Dimension(200, 25));
        
        JLabel locationLabel = new JLabel("Location:");
        locationField = new JTextField(20);
        locationField.setPreferredSize(new Dimension(200, 25));
        
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(20);
        phoneField.setPreferredSize(new Dimension(200, 25));
        
        // Buttons
        signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(100, 30));
        signupButton.setBackground(new Color(34, 139, 34));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        
        backButton = new JButton("Back to Login");
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        
        // Add components to the frame
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Name row
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(nameField, gbc);
        
        // Email row
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(emailField, gbc);
        
        // Password row
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);
        
        // Confirm Password row
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(confirmPasswordField, gbc);
        
        // Role row
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(roleComboBox, gbc);
        
        // Location row
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(locationLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(locationField, gbc);
        
        // Phone row
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(phoneField, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup form layout
     */
    private void setupLayout() {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Signup button action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginForm();
            }
        });
        
        // Enter key on confirm password field
        confirmPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }
        });
    }
    
    /**
     * Perform user registration
     */
    private void performSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();
        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        
        // Validate input
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Convert role string to enum
        User.UserRole role;
        switch (selectedRole) {
            case "Donor":
                role = User.UserRole.DONOR;
                break;
            case "Receiver":
                role = User.UserRole.RECEIVER;
                break;
            case "Charity":
                role = User.UserRole.CHARITY;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Please select a valid role.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        // Attempt registration
        User user = authController.register(name, email, password, role, location, phone);
        
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            openLoginForm();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may already exist or invalid input.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open login form
     */
    private void openLoginForm() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
}
