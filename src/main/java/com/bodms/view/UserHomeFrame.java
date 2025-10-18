package com.bodms.view;

import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;

public class UserHomeFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;

    public UserHomeFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Home - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel logo = new JLabel("BLOOD AND ORGAN DONATION", SwingConstants.CENTER);
        logo.setFont(logo.getFont().deriveFont(Font.BOLD, 22f));
        add(logo, gbc);

        gbc.gridy++;
        GradientButton donation = new GradientButton("Donation");
        add(donation, gbc);
        gbc.gridy++;
        GradientButton reception = new GradientButton("Reception");
        add(reception, gbc);
        gbc.gridy++;
        GradientButton charity = new GradientButton("Charity");
        add(charity, gbc);

        donation.addActionListener(e -> { setVisible(false); new DonationChoiceFrame(currentUser, this).setVisible(true); });
        reception.addActionListener(e -> { setVisible(false); new ReceptionChoiceFrame(currentUser, this).setVisible(true); });
        charity.addActionListener(e -> { setVisible(false); new CharityMenuFrame(currentUser, this).setVisible(true); });

        // Return button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }
}
