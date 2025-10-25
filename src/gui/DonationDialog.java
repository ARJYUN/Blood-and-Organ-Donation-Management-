package gui;

import database.CharityDAO;
import interfaces.PaymentGateway;
import interfaces.DemoPaymentGateway;
import interfaces.UPIPaymentGateway;
import models.CharityRequest;
import models.Donation;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Donation Dialog - Mock payment gateway interface
 * Demonstrates Abstraction through PaymentGateway interface
 */
public class DonationDialog extends JDialog {
    private CharityRequest request;
    private CharityPanel parentPanel;
    private CharityDAO charityDAO;
    
    private JTextField donorNameField, amountField;
    private JComboBox<String> paymentMethodCombo;
    private JTextField cardNumberField, cvvField;
    private JButton donateButton, cancelButton;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    private static final Color BLACK = Color.BLACK;

    
    public DonationDialog(CharityPanel parent, CharityRequest request) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Make a Donation", true);
        this.parentPanel = parent;
        this.request = request;
        this.charityDAO = new CharityDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(500, 70));
        
        JLabel headerLabel = new JLabel("Donate to: " + request.getTitle());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
        
        // Donor Name
        addFormField(formPanel, gbc, row++, "Your Name:", donorNameField = new JTextField(20));
        
        // Amount
        addFormField(formPanel, gbc, row++, "Amount (₹):", amountField = new JTextField(20));
        
        // Payment Method
        String[] paymentMethods = {"Credit/Debit Card", "UPI"};
        paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.addActionListener(e -> updatePaymentFields());
        addFormField(formPanel, gbc, row++, "Payment Method:", paymentMethodCombo);
        
        // Card Number / UPI ID
        addFormField(formPanel, gbc, row++, "Card Number:", cardNumberField = new JTextField(20));
        
        // CVV / UPI PIN
        addFormField(formPanel, gbc, row++, "CVV:", cvvField = new JTextField(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(WHITE);
        
        donateButton = new JButton("DONATE");
        donateButton.setFont(new Font("Arial", Font.BOLD, 14));
        donateButton.setBackground(PRIMARY_RED);
        donateButton.setForeground(WHITE);
        donateButton.setFocusPainted(false);
        donateButton.setBorderPainted(false);
        donateButton.setPreferredSize(new Dimension(150, 40));
        donateButton.addActionListener(e -> processDonation());
        
        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setBackground(WHITE);
        cancelButton.setForeground(PRIMARY_RED);
        cancelButton.setBorder(BorderFactory.createLineBorder(PRIMARY_RED, 2));
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(donateButton);
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
    
    private void updatePaymentFields() {
        String method = (String) paymentMethodCombo.getSelectedItem();
        
        if ("UPI".equals(method)) {
            ((JLabel) ((JPanel) cardNumberField.getParent()).getComponent(
                ((JPanel) cardNumberField.getParent()).getComponentZOrder(cardNumberField) - 1))
                .setText("UPI ID:");
            ((JLabel) ((JPanel) cvvField.getParent()).getComponent(
                ((JPanel) cvvField.getParent()).getComponentZOrder(cvvField) - 1))
                .setText("UPI PIN:");
            cardNumberField.setText("");
            cvvField.setText("");
        } else {
            ((JLabel) ((JPanel) cardNumberField.getParent()).getComponent(
                ((JPanel) cardNumberField.getParent()).getComponentZOrder(cardNumberField) - 1))
                .setText("Card Number:");
            ((JLabel) ((JPanel) cvvField.getParent()).getComponent(
                ((JPanel) cvvField.getParent()).getComponentZOrder(cvvField) - 1))
                .setText("CVV:");
            cardNumberField.setText("");
            cvvField.setText("");
        }
    }
    
    /**
     * Process donation using PaymentGateway interface (Abstraction)
     */
    private void processDonation() {
        try {
            String donorName = donorNameField.getText().trim();
            String amountStr = amountField.getText().trim();
            String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
            String cardNumber = cardNumberField.getText().trim();
            String cvv = cvvField.getText().trim();
            
            // Validation
            if (!ValidationUtils.isNotEmpty(donorName)) {
                ValidationUtils.showError("Please enter your name!");
                return;
            }
            
            if (!ValidationUtils.isNotEmpty(amountStr)) {
                ValidationUtils.showError("Please enter donation amount!");
                return;
            }
            
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                ValidationUtils.showError("Please enter a valid amount!");
                return;
            }
            
            if (!ValidationUtils.isValidAmount(amount)) {
                ValidationUtils.showError("Amount must be greater than 0!");
                return;
            }
            
            // Create appropriate payment gateway (Polymorphism)
            PaymentGateway paymentGateway;
            if ("UPI".equals(paymentMethod)) {
                paymentGateway = new UPIPaymentGateway();
            } else {
                paymentGateway = new DemoPaymentGateway();
            }
            
            // Show processing dialog
            JDialog processingDialog = new JDialog(this, "Processing Payment", true);
            JLabel processingLabel = new JLabel("Processing your payment...", SwingConstants.CENTER);
            processingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            processingDialog.add(processingLabel);
            processingDialog.setSize(300, 100);
            processingDialog.setLocationRelativeTo(this);
            
            // Process payment in background
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() {
                    return paymentGateway.processPayment(donorName, amount, cardNumber, cvv);
                }
                
                @Override
                protected void done() {
                    processingDialog.dispose();
                    try {
                        boolean success = get();
                        
                        if (success) {
                            // Save donation to database
                            Donation donation = new Donation(donorName, request.getRequestId(), 
                                                           amount, paymentGateway.getGatewayName());
                            
                            if (charityDAO.addDonation(donation)) {
                                String message = "Donation successful!\n" +
                                               "Transaction ID: " + paymentGateway.getTransactionId() + "\n" +
                                               "Amount: ₹" + String.format("%.2f", amount) + "\n" +
                                               "Thank you for your generosity!";
                                
                                JOptionPane.showMessageDialog(DonationDialog.this, 
                                    message, 
                                    "Success", 
                                    JOptionPane.INFORMATION_MESSAGE);
                                
                                parentPanel.refreshRequests();
                                dispose();
                            } else {
                                ValidationUtils.showError("Payment processed but failed to record donation!");
                            }
                        } else {
                            ValidationUtils.showSuccess("Thank you for your generous support.\n" + //
                                                                "Your kindness and compassion make a real difference in the lives of those in need. Because of you, we can continue our mission to bring hope and help to our community. We are deeply grateful for your trust and generosity.");
                        }
                    } catch (Exception e) {
                        ValidationUtils.showError("Error processing payment: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            };
            
            worker.execute();
            processingDialog.setVisible(true);
            
        } catch (Exception ex) {
            ValidationUtils.showError("Donation failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
