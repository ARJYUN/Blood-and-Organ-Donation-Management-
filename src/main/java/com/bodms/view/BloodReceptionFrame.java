package com.bodms.view;

import com.bodms.controller.ReceiverController;
import com.bodms.database.HospitalDAO;
import com.bodms.model.Hospital;
import com.bodms.model.Receiver;
import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BloodReceptionFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;
    private final ReceiverController controller = new ReceiverController();
    private final HospitalDAO hospitalDAO = new HospitalDAO();

    private final JComboBox<String> bloodNeeded = new JComboBox<>(new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"});
    private final JComboBox<Hospital> center = new JComboBox<>();
    private final JComboBox<String> date = new JComboBox<>(new String[]{"Today","Tomorrow","In 3 days","Next week"});

    public BloodReceptionFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Blood Reception - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Blood Reception", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++; add(new JLabel("Select Blood Group needed"), gbc);
        gbc.gridy++; add(bloodNeeded, gbc);
        gbc.gridy++; add(new JLabel("Select nearest center to collect"), gbc);
        gbc.gridy++; add(center, gbc);
        gbc.gridy++; add(new JLabel("Select Date"), gbc);
        gbc.gridy++; add(date, gbc);
        gbc.gridy++;
        GradientButton submit = new GradientButton("Submit");
        add(submit, gbc);

        submit.addActionListener(e -> onSubmit());
        loadCenters("blood");

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        back.addActionListener(e -> { if (previous != null) previous.setVisible(true); dispose(); });
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }

    private void loadCenters(String key) {
        center.removeAllItems();
        List<Hospital> list = hospitalDAO.findNearby(key, "");
        for (Hospital h : list) center.addItem(h);
        center.setRenderer((list1, value, index, isSelected, cellHasFocus) ->
                new DefaultListCellRenderer().getListCellRendererComponent(list1, value==null?"":((Hospital)value).getName(), index, isSelected, cellHasFocus));
    }

    private void onSubmit() {
        Receiver r = new Receiver();
        r.setName(currentUser.getName());
        r.setBloodGroupNeeded((String) bloodNeeded.getSelectedItem());
        r.setOrganNeeded(null);
        Hospital h = (Hospital) center.getSelectedItem();
        r.setLocation(h==null?"":h.getAddress());
        r.setContactInfo("Center: "+(h==null?"":h.getName())+", Date: "+date.getSelectedItem());
        r.setApproved(false);
        try {
            controller.createOrUpdate(currentUser.getId(), r);
            JOptionPane.showMessageDialog(this, "Request submitted. Admin will review.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
