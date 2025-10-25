package gui;

import database.RecipientDAO;
import database.UserDAO;
import models.Recipient;
import models.User;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Recipient Registration Frame
 */
public class RecipientRegistrationFrame extends JFrame {
    // Color theme
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    
    // Form fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderCombo;
    private JComboBox<String> bloodGroupCombo;
    private JTextField organField;
    private JTextField contactField;
    private JTextField locationField;
    private JComboBox<String> urgencyCombo;
    private JTextArea medicalConditionArea;
    
    public RecipientRegistrationFrame() {
        setTitle("Recipient Registration - Blood & Organ Donation System");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(600, 80));
        
        JLabel titleLabel = new JLabel("RECIPIENT REGISTRATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        int row = 0;
        
        // Username
        addFormField(formPanel, gbc, row++, "Username:", usernameField = new JTextField(20));
        
        // Password
        addFormField(formPanel, gbc, row++, "Password:", passwordField = new JPasswordField(20));
        
        // Confirm Password
        addFormField(formPanel, gbc, row++, "Confirm Password:", confirmPasswordField = new JPasswordField(20));
        
        // Name
        addFormField(formPanel, gbc, row++, "Full Name:", nameField = new JTextField(20));
        
        // Age
        addFormField(formPanel, gbc, row++, "Age:", ageField = new JTextField(20));
        
        // Gender
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        addFormField(formPanel, gbc, row++, "Gender:", genderCombo);
        
        // Blood Group Needed
        bloodGroupCombo = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        addFormField(formPanel, gbc, row++, "Blood Group Needed:", bloodGroupCombo);
        
        // Organ Needed
        addFormField(formPanel, gbc, row++, "Organ Needed (Optional):", organField = new JTextField(20));
        
        // Contact
        addFormField(formPanel, gbc, row++, "Contact Number:", contactField = new JTextField(20));
        
        // Location
        addFormField(formPanel, gbc, row++, "Location:", locationField = new JTextField(20));
        
        // Urgency Level
        urgencyCombo = new JComboBox<>(new String[]{"NORMAL", "URGENT", "CRITICAL"});
        addFormField(formPanel, gbc, row++, "Urgency Level:", urgencyCombo);
        
        // Medical Condition
        gbc.gridx = 0;
        gbc.gridy = row++;
        JLabel medicalLabel = new JLabel("Medical Condition:");
        medicalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(medicalLabel, gbc);
        
        gbc.gridx = 1;
        medicalConditionArea = new JTextArea(3, 20);
        medicalConditionArea.setLineWrap(true);
        medicalConditionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(medicalConditionArea);
        formPanel.add(scrollPane, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(PRIMARY_RED);
        
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(PRIMARY_RED);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(e -> handleRegistration());
        
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private void handleRegistration() {
        // Validate inputs
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String contact = contactField.getText().trim();
        String location = locationField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || 
            ageStr.isEmpty() || contact.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationUtils.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (!ValidationUtils.isValidAge(age)) {
                JOptionPane.showMessageDialog(this, "Age must be between 1 and 120!", 
                                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationUtils.isValidPhoneNumber(contact)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit phone number!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username already exists
        UserDAO userDAO = new UserDAO();
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists! Please choose another.", 
                                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create User object
        User user = new User(username, password, "RECIPIENT");
        int userId = userDAO.registerUser(user);
        
        if (userId > 0) {
            // Create Recipient object
            Recipient recipient = new Recipient(username, password);
            recipient.setUserId(userId);
            recipient.setName(name);
            recipient.setAge(age);
            recipient.setGender((String) genderCombo.getSelectedItem());
            recipient.setBloodGroupNeeded((String) bloodGroupCombo.getSelectedItem());
            recipient.setOrganNeeded(organField.getText().trim());
            recipient.setContact(contact);
            recipient.setLocation(location);
            recipient.setUrgencyLevel((String) urgencyCombo.getSelectedItem());
            recipient.setMedicalCondition(medicalConditionArea.getText().trim());
            
            // Register recipient
            if (RecipientDAO.registerRecipient(recipient)) {
                JOptionPane.showMessageDialog(this, 
                    "Registration successful!\nYou can now login with your credentials.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register recipient details!", 
                                            "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create user account!", 
                                        "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
