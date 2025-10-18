package com.bodms.view;

import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;

public class ReceptionChoiceFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;

    public ReceptionChoiceFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Reception - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Reception", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++;
        add(new JLabel("BLOOD AND ORGAN DONATION", SwingConstants.CENTER), gbc);

        gbc.gridy++;
        GradientButton blood = new GradientButton("Blood");
        add(blood, gbc);
        gbc.gridy++;
        GradientButton organ = new GradientButton("Organ");
        add(organ, gbc);

        blood.addActionListener(e -> { setVisible(false); new BloodReceptionFrame(currentUser, this).setVisible(true); });
        organ.addActionListener(e -> { setVisible(false); new OrganReceptionFrame(currentUser, this).setVisible(true); });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }
}
