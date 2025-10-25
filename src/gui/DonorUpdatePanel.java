package gui;

import database.DonorDAO;
import models.Donor;
import utils.SessionManager;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Donor Update Panel - Update donor information
 */
public class DonorUpdatePanel extends JPanel {
    private DonorDAO donorDAO;
    private Donor currentDonor;
    
    private JTextField nameField, contactField, locationField, organField;
    private JSpinner ageSpinner;
    private JComboBox<String> genderCombo, bloodGroupCombo;
    private JButton updateButton;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public DonorUpdatePanel() {
        donorDAO = new DonorDAO();
        initializeUI();
        loadDonorData();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        
        JLabel headerLabel = new JLabel("Update Information");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        int row = 0;
        
        // Name
        addFormField(formPanel, gbc, row++, "Full Name:", nameField = new JTextField(25));
        
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
        addFormField(formPanel, gbc, row++, "Organ:", organField = new JTextField(25));
        
        // Contact
        addFormField(formPanel, gbc, row++, "Contact:", contactField = new JTextField(25));
        
        // Location
        addFormField(formPanel, gbc, row++, "Location:", locationField = new JTextField(25));
        
        // Update Button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        updateButton = new JButton("UPDATE INFORMATION");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(PRIMARY_RED);
        updateButton.setForeground(WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setPreferredSize(new Dimension(200, 40));
        updateButton.addActionListener(e -> updateDonorInfo());
        
        formPanel.add(updateButton, gbc);
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        
        if (field instanceof JTextField) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_RED, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        panel.add(field, gbc);
    }
    
    private void loadDonorData() {
        int userId = SessionManager.getInstance().getCurrentUserId();
        currentDonor = donorDAO.getDonorByUserId(userId);
        
        if (currentDonor != null) {
            nameField.setText(currentDonor.getName());
            ageSpinner.setValue(currentDonor.getAge());
            genderCombo.setSelectedItem(currentDonor.getGender());
            bloodGroupCombo.setSelectedItem(currentDonor.getBloodGroup());
            organField.setText(currentDonor.getOrgan());
            contactField.setText(currentDonor.getContact());
            locationField.setText(currentDonor.getLocation());
        }
    }
    
    private void updateDonorInfo() {
        if (currentDonor == null) {
            ValidationUtils.showError("Unable to load donor information!");
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            int age = (Integer) ageSpinner.getValue();
            String gender = (String) genderCombo.getSelectedItem();
            String bloodGroup = (String) bloodGroupCombo.getSelectedItem();
            String organ = organField.getText().trim();
            String contact = contactField.getText().trim();
            String location = locationField.getText().trim();
            
            // Validation
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
            
            // Update donor object
            currentDonor.setName(name);
            currentDonor.setAge(age);
            currentDonor.setGender(gender);
            currentDonor.setBloodGroup(bloodGroup);
            currentDonor.setOrgan(organ.isEmpty() ? "None" : organ);
            currentDonor.setContact(contact);
            currentDonor.setLocation(location);
            
            if (donorDAO.updateDonor(currentDonor)) {
                ValidationUtils.showSuccess("Information updated successfully!");
            } else {
                ValidationUtils.showError("Failed to update information!");
            }
            
        } catch (Exception ex) {
            ValidationUtils.showError("Update failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
