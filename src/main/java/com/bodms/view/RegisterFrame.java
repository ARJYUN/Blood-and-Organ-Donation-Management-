package com.bodms.view;

import com.bodms.controller.AuthController;
import com.bodms.model.User;
import com.bodms.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JDialog {
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JComboBox<String> roleCombo = new JComboBox<>(new String[]{"DONOR","RECEIVER","CHARITY"});
    private final AuthController auth = new AuthController();

    public RegisterFrame(Frame owner) {
        super(owner, "Register", true);
        setSize(420, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0; form.add(new JLabel("Name:"), gbc);
        gbc.gridx=1; gbc.fill=GridBagConstraints.HORIZONTAL; gbc.weightx=1; nameField.setColumns(20); form.add(nameField, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.fill=GridBagConstraints.NONE; gbc.weightx=0; form.add(new JLabel("Email:"), gbc);
        gbc.gridx=1; gbc.fill=GridBagConstraints.HORIZONTAL; gbc.weightx=1; form.add(emailField, gbc);
        gbc.gridx=0; gbc.gridy=2; gbc.fill=GridBagConstraints.NONE; gbc.weightx=0; form.add(new JLabel("Password:"), gbc);
        gbc.gridx=1; gbc.fill=GridBagConstraints.HORIZONTAL; gbc.weightx=1; form.add(passwordField, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.fill=GridBagConstraints.NONE; gbc.weightx=0; form.add(new JLabel("Role:"), gbc);
        gbc.gridx=1; gbc.fill=GridBagConstraints.HORIZONTAL; gbc.weightx=1; form.add(roleCombo, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelBtn = new JButton("Cancel");
        JButton submitBtn = new JButton("Register");
        buttons.add(cancelBtn);
        buttons.add(submitBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> dispose());
        submitBtn.addActionListener(e -> onSubmit());
    }

    private void onSubmit() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pwd = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();

        if (!ValidationUtils.notBlank(name) || !ValidationUtils.isEmail(email) || pwd.length() < 6) {
            JOptionPane.showMessageDialog(this, "Please enter valid details (password >= 6 chars)", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            User u = auth.register(name, email, pwd, role);
            JOptionPane.showMessageDialog(this, "Registered successfully. You can now login.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
