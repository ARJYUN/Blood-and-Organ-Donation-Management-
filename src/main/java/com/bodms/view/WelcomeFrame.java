package com.bodms.view;

import com.bodms.utils.ui.GradientButton;
import com.bodms.utils.ui.GradientPanel;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame() {
        setTitle("Welcome - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left section
        JPanel left = new JPanel();
        left.setBackground(new Color(0xF5F6FA));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 40));

        JLabel welcome = new JLabel("Welcome To");
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 28f));
        JLabel title1 = new JLabel("Blood & Organ");
        title1.setFont(title1.getFont().deriveFont(Font.BOLD, 36f));
        JLabel title2 = new JLabel("Donation");
        title2.setFont(title2.getFont().deriveFont(Font.BOLD, 36f));
        JLabel title3 = new JLabel("Management");
        title3.setFont(title3.getFont().deriveFont(Font.BOLD, 36f));
        left.add(welcome); left.add(Box.createVerticalStrut(10));
        left.add(title1); left.add(title2); left.add(title3);
        left.add(Box.createVerticalStrut(30));

        GradientButton login = new GradientButton("Login");
        GradientButton register = new GradientButton("Register");
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        register.setAlignmentX(Component.LEFT_ALIGNMENT);
        login.addActionListener(e -> { setVisible(false); new LoginFrame(this).setVisible(true); });
        register.addActionListener(e -> new RegisterFrame(this).setVisible(true));
        left.add(login); left.add(Box.createVerticalStrut(12)); left.add(register);

        // Right gradient with logo placeholder
        GradientPanel right = new GradientPanel();
        right.setLayout(new GridBagLayout());
        JLabel logo = new JLabel("BLOOD\nAND ORGAN DONATION", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(logo.getFont().deriveFont(Font.BOLD, 22f));
        right.add(logo);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(0.46);
        split.setEnabled(false);
        add(split, BorderLayout.CENTER);
    }
}
