package com.bodms.view;

import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;

public class DonateMoneyFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;

    public DonateMoneyFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Donate Money - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Donate Money", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++;
        add(new JLabel("BLOOD AND ORGAN DONATION", SwingConstants.CENTER), gbc);

        gbc.gridy++;
        GradientButton details = new GradientButton("Name & Details");
        add(details, gbc);
        gbc.gridy++;
        GradientButton payment = new GradientButton("Payment");
        add(payment, gbc);

        details.addActionListener(e -> showDetailsDialog());
        payment.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mock payment: redirecting to payment gateway..."));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }

    private void showDetailsDialog() {
        JTextField name = new JTextField();
        JTextField amount = new JTextField();
        JTextArea note = new JTextArea(4, 20);
        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("Name")); p.add(name);
        p.add(new JLabel("Amount (USD)")); p.add(amount);
        p.add(new JLabel("Note")); p.add(new JScrollPane(note));
        int ok = JOptionPane.showConfirmDialog(this, p, "Enter Details", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Thank you, "+name.getText()+"! We will process $"+amount.getText()+".");
        }
    }
}
