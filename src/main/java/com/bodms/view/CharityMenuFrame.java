package com.bodms.view;

import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;

public class CharityMenuFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;

    public CharityMenuFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Charity - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Charity", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++;
        add(new JLabel("BLOOD AND ORGAN DONATION", SwingConstants.CENTER), gbc);

        gbc.gridy++;
        GradientButton donate = new GradientButton("Donate");
        add(donate, gbc);
        gbc.gridy++;
        GradientButton request = new GradientButton("Request");
        add(request, gbc);

        donate.addActionListener(e -> { setVisible(false); new DonateMoneyFrame(currentUser, this).setVisible(true); });
        request.addActionListener(e -> { setVisible(false); new RequestMoneyFrame(currentUser, this).setVisible(true); });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }
}
