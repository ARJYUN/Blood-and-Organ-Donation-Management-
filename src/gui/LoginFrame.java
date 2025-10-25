package gui;

import database.UserDAO;
import models.User;
import utils.SessionManager;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Frame for user authentication
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserDAO userDAO;
    
    // Color theme
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    
    public LoginFrame() {
        userDAO = new UserDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Blood & Organ Donation Management System - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(500, 150));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Blood & Organ Donation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(WHITE);
        
        JLabel subtitleLabel = new JLabel("Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(WHITE);
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(PRIMARY_RED);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Login title
        JLabel loginLabel = new JLabel("Login to Your Account");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(DARK_RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(loginLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(passwordField, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(PRIMARY_RED);
        loginButton.setForeground(WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addActionListener(new LoginActionListener());
        formPanel.add(loginButton, gbc);
        
        // Register as Donor button
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 5, 10);
        
        registerButton = new JButton("Register as Donor");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setBackground(WHITE);
        registerButton.setForeground(PRIMARY_RED);
        registerButton.setBorder(BorderFactory.createLineBorder(PRIMARY_RED, 2));
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(200, 35));
        registerButton.addActionListener(e -> openDonorRegistrationFrame());
        formPanel.add(registerButton, gbc);
        
        // Register as Recipient button
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 10, 10);
        
        JButton recipientRegisterButton = new JButton("Register as Recipient");
        recipientRegisterButton.setFont(new Font("Arial", Font.PLAIN, 14));
        recipientRegisterButton.setBackground(WHITE);
        recipientRegisterButton.setForeground(DARK_RED);
        recipientRegisterButton.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        recipientRegisterButton.setFocusPainted(false);
        recipientRegisterButton.setPreferredSize(new Dimension(200, 35));
        recipientRegisterButton.addActionListener(e -> openRecipientRegistrationFrame());
        formPanel.add(recipientRegisterButton, gbc);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Enter key listener for password field
        passwordField.addActionListener(new LoginActionListener());
    }
    
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (!ValidationUtils.isNotEmpty(username) || !ValidationUtils.isNotEmpty(password)) {
                ValidationUtils.showError("Please enter both username and password!");
                return;
            }
            
            try {
                User user = userDAO.authenticateUser(username, password);
                
                if (user != null) {
                    SessionManager.getInstance().setCurrentUser(user);
                    ValidationUtils.showSuccess("Login successful! Welcome " + username);
                    
                    // Open appropriate dashboard based on role
                    dispose();
                    new MainDashboard().setVisible(true);
                } else {
                    ValidationUtils.showError("Invalid username or password!");
                    passwordField.setText("");
                }
            } catch (Exception ex) {
                ValidationUtils.showError("Login failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void openDonorRegistrationFrame() {
        new DonorRegistrationFrame().setVisible(true);
    }
    
    private void openRecipientRegistrationFrame() {
        new RecipientRegistrationFrame().setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}
