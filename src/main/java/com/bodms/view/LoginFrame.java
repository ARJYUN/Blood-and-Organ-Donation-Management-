package com.bodms.view;

import com.bodms.controller.AuthController;
import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;
import com.bodms.utils.ui.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginFrame extends JFrame {
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final AuthController auth = new AuthController();
    private final JFrame previous;

    public LoginFrame(JFrame previous) {
        this.previous = previous;
        setTitle("BODMS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Login");
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 32f));
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xF5F6FA));
        RoundedPanel card = new RoundedPanel(30, new Color(0xDDDDDD));
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel icon = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        icon.setFont(icon.getFont().deriveFont(40f));
        card.add(icon, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        card.add(new JLabel("Email"), gbc);
        gbc.gridx = 1; emailField.setColumns(24); card.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy++; card.add(new JLabel("Password"), gbc);
        gbc.gridx = 1; card.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        GradientButton loginBtn = new GradientButton("LOGIN");
        card.add(loginBtn, gbc);

        center.add(card);
        add(center, BorderLayout.CENTER);

        JPanel rightStripe = new JPanel();
        rightStripe.setBackground(new Color(0xFF6A00));
        rightStripe.setPreferredSize(new Dimension(40, 10));
        add(rightStripe, BorderLayout.EAST);

        // Back/Return
        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        south.add(back);
        add(south, BorderLayout.SOUTH);
        back.addActionListener(e -> {
            if (previous != null) previous.setVisible(true);
            dispose();
        });

        loginBtn.addActionListener(e -> onLogin());
    }

    private void onLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        Optional<User> res = auth.login(email, password);
        if (res.isPresent()) {
            User u = res.get();
            setVisible(false);
            if ("ADMIN".equals(u.getRole())) new AdminDashboardFrame(u, this).setVisible(true);
            else new UserHomeFrame(u, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
