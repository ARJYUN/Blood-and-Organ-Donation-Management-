package gui;

import database.CharityDAO;
import models.Donation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * View Donations Dialog - Display donations for a charity request
 */
public class ViewDonationsDialog extends JDialog {
    private CharityDAO charityDAO;
    private int requestId;
    private JTable donationsTable;
    private DefaultTableModel tableModel;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public ViewDonationsDialog(int requestId, String title) {
        super((Frame) null, "Donations for: " + title, true);
        this.requestId = requestId;
        this.charityDAO = new CharityDAO();
        initializeUI();
        loadDonations();
    }
    
    private void initializeUI() {
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(700, 60));
        
        JLabel headerLabel = new JLabel("Donation History");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Table
        String[] columns = {"Donation ID", "Donor Name", "Amount (₹)", "Payment Method", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        donationsTable = new JTable(tableModel);
        donationsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        donationsTable.setRowHeight(30);
        donationsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        donationsTable.getTableHeader().setBackground(PRIMARY_RED);
        donationsTable.getTableHeader().setForeground(WHITE);
        
        JScrollPane scrollPane = new JScrollPane(donationsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);
        
        JButton closeButton = new JButton("CLOSE");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setBackground(PRIMARY_RED);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(150, 35));
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(closeButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadDonations() {
        tableModel.setRowCount(0);
        List<Donation> donations = charityDAO.getDonationsByRequestId(requestId);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        
        if (donations.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No donations yet for this request", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Donation donation : donations) {
                Object[] row = {
                    donation.getDonationId(),
                    donation.getDonorName(),
                    String.format("%.2f", donation.getAmount()),
                    donation.getPaymentMethod(),
                    dateFormat.format(donation.getDonationDate())
                };
                tableModel.addRow(row);
            }
        }
    }
}
