package com.bodms.view;

import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;

public class RequestMoneyFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;

    public RequestMoneyFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Request Money/Vaccines - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Request Money/Vaccines", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++;
        add(new JLabel("BLOOD AND ORGAN DONATION", SwingConstants.CENTER), gbc);

        gbc.gridy++;
        GradientButton enter = new GradientButton("Enter Amount/Vaccine needed");
        add(enter, gbc);
        gbc.gridy++;
        GradientButton request = new GradientButton("Request");
        add(request, gbc);

        enter.addActionListener(e -> showRequestDialog());
        request.addActionListener(e -> JOptionPane.showMessageDialog(this, "Request submitted successfully."));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }

    private void showRequestDialog() {
        JTextField amount = new JTextField();
        JTextField vaccine = new JTextField();
        JTextArea reason = new JTextArea(4, 20);
        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("Amount (optional)")); p.add(amount);
        p.add(new JLabel("Vaccine (optional)")); p.add(vaccine);
        p.add(new JLabel("Reason")); p.add(new JScrollPane(reason));
        JOptionPane.showConfirmDialog(this, p, "Request Details", JOptionPane.OK_CANCEL_OPTION);
    }
}
