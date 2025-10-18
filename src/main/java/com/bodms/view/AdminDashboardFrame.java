package com.bodms.view;

import com.bodms.controller.AdminController;
import com.bodms.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    private final AdminController admin = new AdminController();
    private final User currentUser;

    private final DefaultTableModel usersModel = new DefaultTableModel(new String[]{"ID","Name","Email","Role"}, 0);
    private final DefaultTableModel donorsModel = new DefaultTableModel(new String[]{"ID","Name","Blood","Organ","Location","Contact","Available","Approved"}, 0);
    private final DefaultTableModel receiversModel = new DefaultTableModel(new String[]{"ID","Name","Blood Needed","Organ Needed","Location","Contact","Approved"}, 0);
    private final DefaultTableModel charitiesModel = new DefaultTableModel(new String[]{"ID","Name","Purpose","Contact","Funds"}, 0);
    private final DefaultTableModel hospitalsModel = new DefaultTableModel(new String[]{"ID","Name","Address","Contact","Organs"}, 0);

    public AdminDashboardFrame(User u, JFrame previous) {
        this.currentUser = u;
        setTitle("Admin Dashboard - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Users", usersPanel());
        tabs.addTab("Donors", donorsPanel());
        tabs.addTab("Receivers", receiversPanel());
        tabs.addTab("Charities", charitiesPanel());
        tabs.addTab("Hospitals", hospitalsPanel());
        add(tabs, BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Return");
        top.add(back);
        back.addActionListener(e -> {
            dispose();
            // Return to login
            new LoginFrame(null).setVisible(true);
        });
        add(top, BorderLayout.NORTH);

        refreshAll();
    }

    private JPanel usersPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(usersModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton del = new JButton("Delete Selected");
        del.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) usersModel.getValueAt(row, 0);
                try { admin.deleteUser(id); refreshUsers(); } catch (SQLException ex) { showErr(ex); }
            }
        });
        p.add(del, BorderLayout.SOUTH);
        return p;
    }

    private JPanel donorsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(donorsModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel actions = new JPanel();
        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");
        JButton delete = new JButton("Delete");
        actions.add(approve); actions.add(reject); actions.add(delete);
        approve.addActionListener(e -> toggleDonor(table, true));
        reject.addActionListener(e -> toggleDonor(table, false));
        delete.addActionListener(e -> deleteDonor(table));
        p.add(actions, BorderLayout.SOUTH);
        return p;
    }

    private JPanel receiversPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(receiversModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel actions = new JPanel();
        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");
        JButton delete = new JButton("Delete");
        actions.add(approve); actions.add(reject); actions.add(delete);
        approve.addActionListener(e -> toggleReceiver(table, true));
        reject.addActionListener(e -> toggleReceiver(table, false));
        delete.addActionListener(e -> deleteReceiver(table));
        p.add(actions, BorderLayout.SOUTH);
        return p;
    }

    private JPanel charitiesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(charitiesModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton delete = new JButton("Delete Selected");
        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) charitiesModel.getValueAt(row, 0);
                try { admin.deleteCharity(id); refreshCharities(); } catch (SQLException ex) { showErr(ex); }
            }
        });
        p.add(delete, BorderLayout.SOUTH);
        return p;
    }

    private JPanel hospitalsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(hospitalsModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 6, 6));
        JTextField name = new JTextField();
        JTextField addr = new JTextField();
        JTextField contact = new JTextField();
        JTextField organs = new JTextField();
        JButton add = new JButton("Add/Update");
        JButton delete = new JButton("Delete Selected");
        form.add(new JLabel("Name")); form.add(new JLabel("Address")); form.add(new JLabel("Contact")); form.add(new JLabel("Organs")); form.add(new JLabel(""));
        form.add(name); form.add(addr); form.add(contact); form.add(organs); form.add(add);
        p.add(form, BorderLayout.NORTH);
        p.add(delete, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            try {
                Hospital h = new Hospital();
                h.setName(name.getText().trim());
                h.setAddress(addr.getText().trim());
                h.setContact(contact.getText().trim());
                h.setLinkedOrgans(organs.getText().trim());
                admin.addHospital(h);
                refreshHospitals();
            } catch (SQLException ex) { showErr(ex); }
        });
        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row>=0) {
                int id = (int) hospitalsModel.getValueAt(row, 0);
                try { admin.deleteHospital(id); refreshHospitals(); } catch (SQLException ex) { showErr(ex); }
            }
        });
        return p;
    }

    private void toggleDonor(JTable table, boolean approved) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) donorsModel.getValueAt(row, 0);
            try { admin.approveDonor(id, approved); refreshDonors(); } catch (SQLException ex) { showErr(ex); }
        }
    }

    private void deleteDonor(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) donorsModel.getValueAt(row, 0);
            try { admin.deleteDonor(id); refreshDonors(); } catch (SQLException ex) { showErr(ex); }
        }
    }

    private void toggleReceiver(JTable table, boolean approved) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) receiversModel.getValueAt(row, 0);
            try { admin.approveReceiver(id, approved); refreshReceivers(); } catch (SQLException ex) { showErr(ex); }
        }
    }

    private void deleteReceiver(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) receiversModel.getValueAt(row, 0);
            try { admin.deleteReceiver(id); refreshReceivers(); } catch (SQLException ex) { showErr(ex); }
        }
    }

    private void refreshAll() { refreshUsers(); refreshDonors(); refreshReceivers(); refreshCharities(); refreshHospitals(); }
    private void refreshUsers() {
        usersModel.setRowCount(0);
        for (User u : admin.listUsers()) usersModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getRole()});
    }
    private void refreshDonors() {
        donorsModel.setRowCount(0);
        for (Donor d : admin.listDonors()) donorsModel.addRow(new Object[]{d.getId(), d.getName(), d.getBloodGroup(), d.getOrgan(), d.getLocation(), d.getContactInfo(), d.isAvailability(), d.isApproved()});
    }
    private void refreshReceivers() {
        receiversModel.setRowCount(0);
        for (Receiver r : admin.listReceivers()) receiversModel.addRow(new Object[]{r.getId(), r.getName(), r.getBloodGroupNeeded(), r.getOrganNeeded(), r.getLocation(), r.getContactInfo(), r.isApproved()});
    }
    private void refreshCharities() {
        charitiesModel.setRowCount(0);
        for (Charity c : admin.listCharities()) charitiesModel.addRow(new Object[]{c.getId(), c.getName(), c.getPurpose(), c.getContactInfo(), c.getFunds()});
    }
    private void refreshHospitals() {
        hospitalsModel.setRowCount(0);
        for (Hospital h : admin.listHospitals()) hospitalsModel.addRow(new Object[]{h.getId(), h.getName(), h.getAddress(), h.getContact(), h.getLinkedOrgans()});
    }

    private void showErr(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
}
