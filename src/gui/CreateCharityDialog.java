package gui;

import database.CharityDAO;
import models.CharityRequest;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Create Charity Dialog - Create new charity requests
 */
public class CreateCharityDialog extends JDialog {
    private CharityManagementPanel parentPanel;
    private CharityDAO charityDAO;
    
    private JTextField titleField, requesterField, goalField;
    private JTextArea descriptionArea;
    private JComboBox<String> typeCombo;
    private JButton createButton, cancelButton;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public CreateCharityDialog(CharityManagementPanel parent) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Create Charity Request", true);
        this.parentPanel = parent;
        this.charityDAO = new CharityDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setSize(550, 550);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(550, 60));
        
        JLabel headerLabel = new JLabel("Create New Charity Request");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        int row = 0;
        
        // Title
        addFormField(formPanel, gbc, row++, "Title:", titleField = new JTextField(25));
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(descLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        
        descriptionArea = new JTextArea(5, 25);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descScrollPane, gbc);
        
        row++;
        
        // Requester Name
        addFormField(formPanel, gbc, row++, "Requester Name:", requesterField = new JTextField(25));
        
        // Type
        String[] types = {"Medical", "Event", "Education", "Emergency", "Other"};
        typeCombo = new JComboBox<>(types);
        addFormField(formPanel, gbc, row++, "Type:", typeCombo);
        
        // Goal Amount
        addFormField(formPanel, gbc, row++, "Goal Amount (₹):", goalField = new JTextField(25));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(WHITE);
        
        createButton = new JButton("CREATE");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(PRIMARY_RED);
        createButton.setForeground(WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.setPreferredSize(new Dimension(150, 40));
        createButton.addActionListener(e -> createRequest());
        
        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setBackground(WHITE);
        cancelButton.setForeground(PRIMARY_RED);
        cancelButton.setBorder(BorderFactory.createLineBorder(PRIMARY_RED, 2));
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
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
        
        if (field instanceof JTextField) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_RED, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        panel.add(field, gbc);
    }
    
    private void createRequest() {
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String requester = requesterField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();
            String goalStr = goalField.getText().trim();
            
            // Validation
            if (!ValidationUtils.isNotEmpty(title)) {
                ValidationUtils.showError("Title is required!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(description)) {
                ValidationUtils.showError("Description is required!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(requester)) {
                ValidationUtils.showError("Requester name is required!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(goalStr)) {
                ValidationUtils.showError("Goal amount is required!");
                return;
            }
            
            double goalAmount;
            try {
                goalAmount = Double.parseDouble(goalStr);
            } catch (NumberFormatException e) {
                ValidationUtils.showError("Please enter a valid goal amount!");
                return;
            }
            
            if (!ValidationUtils.isValidAmount(goalAmount)) {
                ValidationUtils.showError("Goal amount must be greater than 0!");
                return;
            }
            
            // Create charity request
            CharityRequest request = new CharityRequest(title, description, requester, type, goalAmount);
            
            int requestId = charityDAO.createCharityRequest(request);
            
            if (requestId > 0) {
                ValidationUtils.showSuccess("Charity request created successfully!");
                parentPanel.refreshTable();
                dispose();
            } else {
                ValidationUtils.showSuccess("Payment Success");
            }
            
        } catch (Exception ex) {
            ValidationUtils.showError("Creation failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
