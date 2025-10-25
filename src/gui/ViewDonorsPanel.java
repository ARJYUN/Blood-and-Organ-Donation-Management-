package gui;

import database.DonorDAO;
import models.Donor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View Donors Panel - Display all registered donors
 */
public class ViewDonorsPanel extends JPanel {
    private DonorDAO donorDAO;
    private JTable donorTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public ViewDonorsPanel() {
        donorDAO = new DonorDAO();
        initializeUI();
        loadDonors();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel headerLabel = new JLabel("All Registered Donors");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(WHITE);
        refreshButton.setForeground(PRIMARY_RED);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadDonors());
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        // Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Blood Group", "Organ", "Contact", "Location"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donorTable = new JTable(tableModel);
        donorTable.setFont(new Font("Arial", Font.PLAIN, 12));
        donorTable.setRowHeight(30);
        donorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        donorTable.getTableHeader().setBackground(PRIMARY_RED);
        donorTable.getTableHeader().setForeground(WHITE);
        
        JScrollPane scrollPane = new JScrollPane(donorTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadDonors() {
        tableModel.setRowCount(0);
        List<Donor> donors = donorDAO.getAllDonors();
        
        for (Donor donor : donors) {
            Object[] row = {
                donor.getDonorId(),
                donor.getName(),
                donor.getAge(),
                donor.getGender(),
                donor.getBloodGroup(),
                donor.getOrgan(),
                donor.getContact(),
                donor.getLocation()
            };
            tableModel.addRow(row);
        }
    }
}
