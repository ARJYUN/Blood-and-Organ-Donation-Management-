package com.bodms.view;

import com.bodms.controller.DonorController;
import com.bodms.model.Donor;
import com.bodms.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class DonorFrame extends JFrame {
    private final User currentUser;
    private final DonorController donorController = new DonorController();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Blood","Organ","Location","Contact","Available","Approved"}, 0);

    private final JTextField name = new JTextField();
    private final JTextField blood = new JTextField();
    private final JTextField organ = new JTextField();
    private final JTextField location = new JTextField();
    private final JTextField contact = new JTextField();
    private final JCheckBox available = new JCheckBox("Available");

    public DonorFrame(User u) {
        this.currentUser = u;
        setTitle("Donor Panel - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3, 6, 6, 6));
        form.add(new JLabel("Name")); form.add(new JLabel("Blood")); form.add(new JLabel("Organ")); form.add(new JLabel("Location")); form.add(new JLabel("Contact")); form.add(new JLabel("Status"));
        form.add(name); form.add(blood); form.add(organ); form.add(location); form.add(contact); form.add(available);
        JButton save = new JButton("Save/Update");
        JButton refresh = new JButton("Refresh");
        form.add(new JLabel()); form.add(new JLabel()); form.add(save); form.add(refresh); form.add(new JLabel()); form.add(new JLabel());
        add(form, BorderLayout.NORTH);

        save.addActionListener(e -> onSave());
        refresh.addActionListener(e -> reload());

        reload();
    }

    private void onSave() {
        Donor d = new Donor();
        d.setName(name.getText().trim());
        d.setBloodGroup(blood.getText().trim());
        d.setOrgan(organ.getText().trim());
        d.setLocation(location.getText().trim());
        d.setContactInfo(contact.getText().trim());
        d.setAvailability(available.isSelected());
        d.setApproved(false); // admin approval needed
        try {
            donorController.createOrUpdate(currentUser.getId(), d);
            JOptionPane.showMessageDialog(this, "Saved. Pending admin approval.");
            reload();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reload() {
        model.setRowCount(0);
        for (Donor d : donorController.myDonorProfiles(currentUser.getId())) {
            model.addRow(new Object[]{d.getId(), d.getName(), d.getBloodGroup(), d.getOrgan(), d.getLocation(), d.getContactInfo(), d.isAvailability(), d.isApproved()});
        }
    }
}
