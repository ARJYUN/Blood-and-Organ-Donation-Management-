package com.bodms.view;

import com.bodms.controller.DonorController;
import com.bodms.database.HospitalDAO;
import com.bodms.model.Donor;
import com.bodms.model.Hospital;
import com.bodms.model.User;
import com.bodms.utils.ui.GradientButton;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OrganDonationFrame extends JFrame {
    private final User currentUser;
    private final JFrame previous;
    private final DonorController donorController = new DonorController();
    private final HospitalDAO hospitalDAO = new HospitalDAO();

    private final JComboBox<String> organ = new JComboBox<>(new String[]{"Kidney","Liver","Heart","Lung","Pancreas","Cornea"});
    private final JComboBox<Hospital> center = new JComboBox<>();
    private final JComboBox<String> date = new JComboBox<>(new String[]{"This week","Next week","In 2 weeks"});

    public OrganDonationFrame(User u, JFrame previous) {
        this.currentUser = u;
        this.previous = previous;
        setTitle("Organ Donation - BODMS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heading = new JLabel("Organ Donation", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, gbc);

        gbc.gridy++; add(new JLabel("Select the Organ"), gbc);
        gbc.gridy++; add(organ, gbc);
        gbc.gridy++; add(new JLabel("Select nearest center"), gbc);
        gbc.gridy++; add(center, gbc);
        gbc.gridy++; add(new JLabel("Available Date"), gbc);
        gbc.gridy++; add(date, gbc);
        gbc.gridy++;
        GradientButton submit = new GradientButton("Submit");
        add(submit, gbc);

        submit.addActionListener(e -> onSubmit());
        loadCenters("organ");

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
        Donor d = new Donor();
        d.setName(currentUser.getName());
        d.setOrgan((String) organ.getSelectedItem());
        d.setBloodGroup(null);
        Hospital h = (Hospital) center.getSelectedItem();
        d.setLocation(h==null?"":h.getAddress());
        d.setContactInfo("Center: "+(h==null?"":h.getName())+", Date: "+date.getSelectedItem());
        d.setAvailability(true);
        d.setApproved(false);
        try {
            donorController.createOrUpdate(currentUser.getId(), d);
            JOptionPane.showMessageDialog(this, "Thank you! Your availability is submitted for approval.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
