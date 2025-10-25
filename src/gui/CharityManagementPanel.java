package gui;

import database.CharityDAO;
import models.CharityRequest;
import utils.ValidationUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Charity Management Panel - For receptionists to manage charity requests
 */
public class CharityManagementPanel extends JPanel {
    private CharityDAO charityDAO;
    private JTable charityTable;
    private DefaultTableModel tableModel;
    private JButton createButton, refreshButton, viewDonationsButton;
    
    private static final Color PRIMARY_RED_START = new Color(0xFE, 0x5D, 0x26);
    private static final Color PRIMARY_RED_END = new Color(0xEC, 0x1C, 0x24);
    private static final Color WHITE = Color.WHITE;
    private static final Color BLACK = Color.BLACK;
    
    public CharityManagementPanel() {
        charityDAO = new CharityDAO();
        initializeUI();
        loadCharityRequests();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_RED_START, getWidth(), 0, PRIMARY_RED_END);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(950, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel headerLabel = new JLabel("Charity Request Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setOpaque(false);
        
        createButton = new JButton("Create New Request");
        createButton.setBackground(PRIMARY_RED_START);
        createButton.setForeground(BLACK);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.addActionListener(e -> openCreateDialog());
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(PRIMARY_RED_START);
        refreshButton.setForeground(BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.addActionListener(e -> loadCharityRequests());
        
        buttonPanel.add(createButton);
        buttonPanel.add(refreshButton);
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Table
        String[] columns = {"ID", "Title", "Requester", "Type", "Goal (₹)", "Raised (₹)", "Progress %", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        charityTable = new JTable(tableModel);
        charityTable.setFont(new Font("Arial", Font.PLAIN, 12));
        charityTable.setRowHeight(30);
        charityTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        charityTable.getTableHeader().setBackground(PRIMARY_RED_START);
        charityTable.getTableHeader().setForeground(WHITE);
        
        JScrollPane scrollPane = new JScrollPane(charityTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(WHITE);
        
        viewDonationsButton = new JButton("View Donations for Selected");
        viewDonationsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewDonationsButton.setBackground(PRIMARY_RED_START);
        viewDonationsButton.setForeground(WHITE);
        viewDonationsButton.setFocusPainted(false);
        viewDonationsButton.setBorderPainted(false);
        viewDonationsButton.addActionListener(e -> viewDonations());
        
        bottomPanel.add(viewDonationsButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadCharityRequests() {
        tableModel.setRowCount(0);
        List<CharityRequest> requests = charityDAO.getAllCharityRequests();
        
        for (CharityRequest request : requests) {
            Object[] row = {
                request.getRequestId(),
                request.getTitle(),
                request.getRequesterName(),
                request.getType(),
                String.format("%.2f", request.getGoalAmount()),
                String.format("%.2f", request.getRaisedAmount()),
                String.format("%.1f%%", request.getProgressPercentage()),
                request.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void openCreateDialog() {
        new CreateCharityDialog(this).setVisible(true);
    }
    
    private void viewDonations() {
        int selectedRow = charityTable.getSelectedRow();
        
        if (selectedRow == -1) {
            ValidationUtils.showWarning("Please select a charity request first!");
            return;
        }
        
        int requestId = (int) tableModel.getValueAt(selectedRow, 0);
        String title = (String) tableModel.getValueAt(selectedRow, 1);
        
        new ViewDonationsDialog(requestId, title).setVisible(true);
    }
    
    public void refreshTable() {
        loadCharityRequests();
    }
}
