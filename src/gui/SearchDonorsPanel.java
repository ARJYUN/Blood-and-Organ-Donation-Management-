package gui;

import database.DonorDAO;
import models.Donor;
import utils.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Search Donors Panel - Search and filter donors
 * Demonstrates Polymorphism through different search methods
 */
public class SearchDonorsPanel extends JPanel {
    private DonorDAO donorDAO;
    private JComboBox<String> searchTypeCombo;
    private JTextField searchField;
    private JButton searchButton, clearButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    
    private static final Color PRIMARY_RED = new Color(220, 20, 60);
    private static final Color WHITE = Color.WHITE;
    
    public SearchDonorsPanel() {
        donorDAO = new DonorDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_RED);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        
        JLabel headerLabel = new JLabel("Search Donors");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        headerPanel.add(headerLabel);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        searchPanel.setBackground(WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel searchTypeLabel = new JLabel("Search By:");
        searchTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        String[] searchTypes = {"Blood Group", "Organ", "Location"};
        searchTypeCombo = new JComboBox<>(searchTypes);
        searchTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        searchTypeCombo.setPreferredSize(new Dimension(150, 30));
        
        JLabel searchLabel = new JLabel("Search Value:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_RED, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        searchButton = new JButton("SEARCH");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setBackground(PRIMARY_RED);
        searchButton.setForeground(WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.addActionListener(e -> performSearch());
        
        clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 12));
        clearButton.setBackground(WHITE);
        clearButton.setForeground(PRIMARY_RED);
        clearButton.setBorder(BorderFactory.createLineBorder(PRIMARY_RED, 2));
        clearButton.setFocusPainted(false);
        clearButton.setPreferredSize(new Dimension(100, 30));
        clearButton.addActionListener(e -> clearSearch());
        
        searchPanel.add(searchTypeLabel);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        
        // Results Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Blood Group", "Organ", "Contact", "Location"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        resultsTable.getTableHeader().setBackground(PRIMARY_RED);
        resultsTable.getTableHeader().setForeground(WHITE);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Assembly
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Enter key listener
        searchField.addActionListener(e -> performSearch());
    }
    
    /**
     * Polymorphic search method - different behavior based on search type
     */
    private void performSearch() {
        String searchType = (String) searchTypeCombo.getSelectedItem();
        String searchValue = searchField.getText().trim();
        
        if (!ValidationUtils.isNotEmpty(searchValue)) {
            ValidationUtils.showError("Please enter a search value!");
            return;
        }
        
        tableModel.setRowCount(0);
        List<Donor> results = null;
        
        // Polymorphic behavior - different search methods
        switch (searchType) {
            case "Blood Group":
                results = donorDAO.searchByBloodGroup(searchValue);
                break;
            case "Organ":
                results = donorDAO.searchByOrgan(searchValue);
                break;
            case "Location":
                results = donorDAO.searchByLocation(searchValue);
                break;
        }
        
        if (results != null && !results.isEmpty()) {
            for (Donor donor : results) {
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
            
            JOptionPane.showMessageDialog(this, 
                "Found " + results.size() + " matching donor(s)", 
                "Search Results", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            ValidationUtils.showWarning("No donors found matching your search criteria!");
        }
    }
    
    private void clearSearch() {
        searchField.setText("");
        tableModel.setRowCount(0);
    }
}
