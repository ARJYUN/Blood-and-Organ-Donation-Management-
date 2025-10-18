package com.bodms.view;

import com.bodms.controller.ReceiverController;
import com.bodms.model.Donor;
import com.bodms.model.Hospital;
import com.bodms.model.Receiver;
import com.bodms.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class ReceiverFrame extends JFrame {
    private final User currentUser;
    private final ReceiverController controller = new ReceiverController();

    private final JTextField blood = new JTextField();
    private final JTextField organ = new JTextField();
    private final JTextField location = new JTextField();

    private final DefaultTableModel donorsModel = new DefaultTableModel(new String[]{"Name","Blood","Organ","Location","Contact"}, 0);
    private final DefaultTableModel hospitalsModel = new DefaultTableModel(new String[]{"Name","Address","Contact","Organs","Map"}, 0);
    private final DefaultTableModel requestsModel = new DefaultTableModel(new String[]{"ID","Name","Blood Needed","Organ Needed","Location","Contact","Approved"}, 0);

    private final JTextField rName = new JTextField();
    private final JTextField rBlood = new JTextField();
    private final JTextField rOrgan = new JTextField();
    private final JTextField rLocation = new JTextField();
    private final JTextField rContact = new JTextField();

    public ReceiverFrame(User u) {
        this.currentUser = u;
        setTitle("Receiver Panel - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel filters = new JPanel(new GridLayout(2, 6, 6, 6));
        filters.add(new JLabel("Blood Group")); filters.add(new JLabel("Organ")); filters.add(new JLabel("Location")); filters.add(new JLabel("")); filters.add(new JLabel("")); filters.add(new JLabel(""));
        JButton searchDonors = new JButton("Search Donors");
        JButton searchHospitals = new JButton("Nearby Hospitals");
        filters.add(blood); filters.add(organ); filters.add(location); filters.add(searchDonors); filters.add(searchHospitals);
        add(filters, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        JTable donorsTable = new JTable(donorsModel);
        JTable hospitalsTable = new JTable(hospitalsModel);
        JTable requestsTable = new JTable(requestsModel);
        tabs.addTab("Donors", new JScrollPane(donorsTable));
        tabs.addTab("Hospitals", new JScrollPane(hospitalsTable));
        tabs.addTab("My Requests", new JScrollPane(requestsTable));
        add(tabs, BorderLayout.CENTER);

        JPanel reqForm = new JPanel(new GridLayout(2, 6, 6, 6));
        JButton saveReq = new JButton("Create Request");
        reqForm.add(new JLabel("Name")); reqForm.add(new JLabel("Blood Needed")); reqForm.add(new JLabel("Organ Needed")); reqForm.add(new JLabel("Location")); reqForm.add(new JLabel("Contact")); reqForm.add(new JLabel(""));
        reqForm.add(rName); reqForm.add(rBlood); reqForm.add(rOrgan); reqForm.add(rLocation); reqForm.add(rContact); reqForm.add(saveReq);
        add(reqForm, BorderLayout.SOUTH);

        searchDonors.addActionListener(e -> doSearchDonors());
        searchHospitals.addActionListener(e -> doSearchHospitals());
        saveReq.addActionListener(e -> onSaveRequest());

        reloadRequests();
    }

    private void doSearchDonors() {
        donorsModel.setRowCount(0);
        for (Donor d : controller.searchDonors(blood.getText().trim(), organ.getText().trim(), location.getText().trim())) {
            donorsModel.addRow(new Object[]{d.getName(), d.getBloodGroup(), d.getOrgan(), d.getLocation(), d.getContactInfo()});
        }
    }

    private void doSearchHospitals() {
        hospitalsModel.setRowCount(0);
        for (Hospital h : controller.nearbyHospitals(organ.getText().trim(), location.getText().trim())) {
            String map = "https://www.google.com/maps/search/?api=1&query=" + URLEncoder.encode(h.getAddress(), StandardCharsets.UTF_8);
            hospitalsModel.addRow(new Object[]{h.getName(), h.getAddress(), h.getContact(), h.getLinkedOrgans(), map});
        }
    }

    private void onSaveRequest() {
        Receiver r = new Receiver();
        r.setName(rName.getText().trim());
        r.setBloodGroupNeeded(rBlood.getText().trim());
        r.setOrganNeeded(rOrgan.getText().trim());
        r.setLocation(rLocation.getText().trim());
        r.setContactInfo(rContact.getText().trim());
        r.setApproved(false);
        try {
            controller.createOrUpdate(currentUser.getId(), r);
            JOptionPane.showMessageDialog(this, "Request submitted. Pending admin approval.");
            reloadRequests();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadRequests() {
        requestsModel.setRowCount(0);
        for (Receiver r : controller.myRequests(currentUser.getId())) {
            requestsModel.addRow(new Object[]{r.getId(), r.getName(), r.getBloodGroupNeeded(), r.getOrganNeeded(), r.getLocation(), r.getContactInfo(), r.isApproved()});
        }
    }
}
