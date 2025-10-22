package com.blooddonation.view;

import com.blooddonation.controller.AuthController;
import com.blooddonation.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Form for user authentication
 * Provides login interface for both admin and regular users
 */
public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JComboBox<String> roleComboBox;
    private AuthController authController;
    
    public LoginForm() {
        this.authController = new AuthController();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blood Donation Management System - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Initialize form components
     */
    private void initializeComponents() {
        // Title label
        JLabel titleLabel = new JLabel("Blood Donation Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(200, 25));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 25));
        
        // Role selection
        JLabel roleLabel = new JLabel("Login As:");
        String[] roles = {"User", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setPreferredSize(new Dimension(200, 25));
        
        // Buttons
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        
        signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(100, 30));
        signupButton.setBackground(new Color(34, 139, 34));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        
        // Add components to the frame
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Email row
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(emailField, gbc);
        
        // Password row
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);
        
        // Role row
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(roleComboBox, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
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
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Signup button action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignupForm();
            }
        });
        
        // Enter key on password field
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }
    
    /**
     * Perform login authentication
     */
    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();
        
        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Attempt login
        User user = authController.login(email, password);
        
        if (user != null) {
            // Check if user role matches selected role
            boolean isAdmin = "Admin".equals(selectedRole);
            boolean userIsAdmin = user.getRole() == User.UserRole.ADMIN;
            
            if (isAdmin && !userIsAdmin) {
                JOptionPane.showMessageDialog(this, "Access denied. Admin privileges required.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!isAdmin && userIsAdmin) {
                JOptionPane.showMessageDialog(this, "Please select 'Admin' to login as administrator.", "Role Mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Login successful
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open appropriate dashboard
            openDashboard(user);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    /**
     * Open signup form
     */
    private void openSignupForm() {
        SignupForm signupForm = new SignupForm();
        signupForm.setVisible(true);
        this.dispose();
    }
    
    /**
     * Open appropriate dashboard based on user role
     * @param user Logged in user
     */
    private void openDashboard(User user) {
        switch (user.getRole()) {
            case ADMIN:
                AdminDashboard adminDashboard = new AdminDashboard(user);
                adminDashboard.setVisible(true);
                break;
            case DONOR:
                DonorDashboard donorDashboard = new DonorDashboard(user);
                donorDashboard.setVisible(true);
                break;
            case RECEIVER:
                ReceiverDashboard receiverDashboard = new ReceiverDashboard(user);
                receiverDashboard.setVisible(true);
                break;
            case CHARITY:
                CharityDashboard charityDashboard = new CharityDashboard(user);
                charityDashboard.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown user role.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
