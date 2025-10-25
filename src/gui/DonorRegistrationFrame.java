package gui;

import database.UserDAO;
import database.DonorDAO;
import models.User;
import models.Donor;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Donor Registration Frame
 */
public class DonorRegistrationFrame extends JFrame {
    private JTextField usernameField, nameField, contactField, locationField;
    private JPasswordField passwordField, confirmPasswordField;
    private JSpinner ageSpinner;
    private JComboBox<String> genderCombo, bloodGroupCombo;
    private JTextField organField;
    private JButton registerButton, cancelButton;
    
    private UserDAO userDAO;
    private DonorDAO donorDAO;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Color WHITE = Color.WHITE;
    
    public DonorRegistrationFrame() {
        userDAO = new UserDAO();
        donorDAO = new DonorDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Donor Registration");
        setSize(600, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(600, 80));
        
        JLabel headerLabel = new JLabel("Donor Registration");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        
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
        ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 65, 1));
        addFormField(formPanel, gbc, row++, "Age:", ageSpinner);
        
        // Gender
        String[] genders = {"Male", "Female", "Other"};
        genderCombo = new JComboBox<>(genders);
        addFormField(formPanel, gbc, row++, "Gender:", genderCombo);
        
        // Blood Group
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bloodGroupCombo = new JComboBox<>(bloodGroups);
        addFormField(formPanel, gbc, row++, "Blood Group:", bloodGroupCombo);
        
        // Organ
        addFormField(formPanel, gbc, row++, "Organ (Optional):", organField = new JTextField(20));
        
        // Contact
        addFormField(formPanel, gbc, row++, "Contact Number:", contactField = new JTextField(20));
        
        // Location
        addFormField(formPanel, gbc, row++, "Location:", locationField = new JTextField(20));
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(WHITE);
        
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(PRIMARY_RED);
        registerButton.setForeground(WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(e -> registerDonor());
        
        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setBackground(WHITE);
        cancelButton.setForeground(PRIMARY_RED);
        cancelButton.setBorder(BorderFactory.createLineBorder(PRIMARY_RED, 2));
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        
        if (field instanceof JTextField || field instanceof JPasswordField) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            ((JComponent) field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_RED, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        panel.add(field, gbc);
    }
    
    private void registerDonor() {
        try {
            // Validate inputs
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String name = nameField.getText().trim();
            int age = (Integer) ageSpinner.getValue();
            String gender = (String) genderCombo.getSelectedItem();
            String bloodGroup = (String) bloodGroupCombo.getSelectedItem();
            String organ = organField.getText().trim();
            String contact = contactField.getText().trim();
            String location = locationField.getText().trim();
            
            // Validation
            if (!ValidationUtils.isNotEmpty(username)) {
                ValidationUtils.showError("Username is required!");
                return;
            }
            
            if (userDAO.usernameExists(username)) {
                ValidationUtils.showError("Username already exists!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(password) || password.length() < 6) {
                ValidationUtils.showError("Password must be at least 6 characters!");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                ValidationUtils.showError("Passwords do not match!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(name)) {
                ValidationUtils.showError("Name is required!");
                return;
            }
            
            if (!ValidationUtils.isValidAge(age)) {
                ValidationUtils.showError("Age must be between 18 and 65!");
                return;
            }
            
            if (!ValidationUtils.isValidPhone(contact)) {
                ValidationUtils.showError("Please enter a valid 10-digit phone number!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(location)) {
                ValidationUtils.showError("Location is required!");
                return;
            }
            
            // Create user
            User user = new User(username, password, "DONOR");
            int userId = userDAO.registerUser(user);
            
            if (userId > 0) {
                // Create donor
                Donor donor = new Donor(username, password);
                donor.setUserId(userId);
                donor.setName(name);
                donor.setAge(age);
                donor.setGender(gender);
                donor.setBloodGroup(bloodGroup);
                donor.setOrgan(organ.isEmpty() ? "None" : organ);
                donor.setContact(contact);
                donor.setLocation(location);
                
                if (donorDAO.registerDonor(donor, userId)) {
                    ValidationUtils.showSuccess("Registration successful! You can now login.");
                    dispose();
                } else {
                    ValidationUtils.showError("Failed to register donor information!");
                }
            } else {
                ValidationUtils.showError("Failed to create user account!");
            }
            
        } catch (Exception ex) {
            ValidationUtils.showError("Registration failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
