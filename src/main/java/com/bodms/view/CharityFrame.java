package com.bodms.view;

import com.bodms.controller.CharityController;
import com.bodms.model.Campaign;
import com.bodms.model.Charity;
import com.bodms.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class CharityFrame extends JFrame {
    private final User currentUser;
    private final CharityController controller = new CharityController();

    private final DefaultTableModel charitiesModel = new DefaultTableModel(new String[]{"ID","Name","Purpose","Contact","Funds"}, 0);
    private final DefaultTableModel campaignsModel = new DefaultTableModel(new String[]{"ID","Title","Description","Date"}, 0);

    private final JTextField cName = new JTextField();
    private final JTextField cPurpose = new JTextField();
    private final JTextField cContact = new JTextField();
    private final JTextField cFunds = new JTextField();

    private final JTextField title = new JTextField();
    private final JTextField desc = new JTextField();
    private final JTextField date = new JTextField(LocalDate.now().toString());

    public CharityFrame(User u) {
        this.currentUser = u;
        setTitle("Charity Panel - BODMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("My Charity", charityPanel());
        tabs.addTab("Campaigns", campaignPanel());
        add(tabs, BorderLayout.CENTER);

        reloadCharities();
    }

    private JPanel charityPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(charitiesModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 6, 6));
        JButton save = new JButton("Save Charity");
        form.add(new JLabel("Name")); form.add(new JLabel("Purpose")); form.add(new JLabel("Contact")); form.add(new JLabel("Funds")); form.add(new JLabel(""));
        form.add(cName); form.add(cPurpose); form.add(cContact); form.add(cFunds); form.add(save);
        p.add(form, BorderLayout.NORTH);

        save.addActionListener(e -> {
            try {
                Charity c = new Charity();
                c.setName(cName.getText().trim());
                c.setPurpose(cPurpose.getText().trim());
                c.setContactInfo(cContact.getText().trim());
                try { c.setFunds(Double.parseDouble(cFunds.getText().trim())); } catch (Exception ex) { c.setFunds(0); }
                controller.addOrUpdateCharity(currentUser.getId(), c);
                reloadCharities();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    private JPanel campaignPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JTable table = new JTable(campaignsModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 4, 6, 6));
        JButton add = new JButton("Add Campaign");
        JButton del = new JButton("Delete Selected");
        form.add(new JLabel("Title")); form.add(new JLabel("Description")); form.add(new JLabel("Date (YYYY-MM-DD)")); form.add(new JLabel(""));
        form.add(title); form.add(desc); form.add(date); form.add(add);
        p.add(form, BorderLayout.NORTH);
        p.add(del, BorderLayout.SOUTH);

        add.addActionListener(e -> onAddCampaign());
        del.addActionListener(e -> onDeleteCampaign(table));

        return p;
    }

    private Integer selectedCharityId() {
        if (charitiesModel.getRowCount() == 0) return null;
        return (Integer) charitiesModel.getValueAt(0, 0); // only first/own charity
    }

    private void onAddCampaign() {
        Integer cid = selectedCharityId();
        if (cid == null) { JOptionPane.showMessageDialog(this, "Create a charity first"); return; }
        try {
            controller.addCampaign(cid, title.getText().trim(), desc.getText().trim(), date.getText().trim());
            reloadCampaigns(cid);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDeleteCampaign(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) campaignsModel.getValueAt(row, 0);
            try {
                controller.deleteCampaign(id);
                Integer cid = selectedCharityId();
                if (cid != null) reloadCampaigns(cid);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reloadCharities() {
        charitiesModel.setRowCount(0);
        for (Charity c : controller.myCharities(currentUser.getId())) {
            charitiesModel.addRow(new Object[]{c.getId(), c.getName(), c.getPurpose(), c.getContactInfo(), c.getFunds()});
        }
        Integer cid = selectedCharityId();
        if (cid != null) reloadCampaigns(cid);
    }

    private void reloadCampaigns(int charityId) {
        campaignsModel.setRowCount(0);
        for (Campaign c : controller.listCampaigns(charityId)) {
            campaignsModel.addRow(new Object[]{c.getId(), c.getTitle(), c.getDescription(), c.getDate()});
        }
    }
}
