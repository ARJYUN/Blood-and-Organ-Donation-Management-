package com.blooddonation.controller;

import com.blooddonation.database.DonorDAO;
import com.blooddonation.database.ReceiverDAO;
import com.blooddonation.database.UserDAO;
import com.blooddonation.database.HospitalDAO;
import com.blooddonation.database.CharityDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Export Controller
 * Handles data export functionality (CSV and PDF)
 */
public class ExportController {
    private DonorDAO donorDAO;
    private ReceiverDAO receiverDAO;
    private UserDAO userDAO;
    private HospitalDAO hospitalDAO;
    private CharityDAO charityDAO;
    
    public ExportController() {
        this.donorDAO = new DonorDAO();
        this.receiverDAO = new ReceiverDAO();
        this.userDAO = new UserDAO();
        this.hospitalDAO = new HospitalDAO();
        this.charityDAO = new CharityDAO();
    }
    
    /**
     * Export donors data to CSV
     * @param filename Output filename
     * @return true if successful, false otherwise
     */
    public boolean exportDonorsToCSV(String filename) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            // Write header
            String[] header = {"ID", "User ID", "Blood Group", "Organ Type", "Status", "Medical Clearance", "Last Donation", "Notes"};
            writer.writeNext(header);
            
            // Write data
            List<com.blooddonation.model.Donor> donors = donorDAO.getAllDonors();
            for (com.blooddonation.model.Donor donor : donors) {
                String[] row = {
                    String.valueOf(donor.getId()),
                    String.valueOf(donor.getUserId()),
                    donor.getBloodGroup().getDisplayName(),
                    donor.getOrganType().getDisplayName(),
                    donor.getAvailabilityStatus().getDisplayName(),
                    donor.isMedicalClearance() ? "Yes" : "No",
                    donor.getLastDonationDate() != null ? donor.getLastDonationDate().toString() : "Never",
                    donor.getNotes() != null ? donor.getNotes() : ""
                };
                writer.writeNext(row);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting donors to CSV: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Export receivers data to CSV
     * @param filename Output filename
     * @return true if successful, false otherwise
     */
    public boolean exportReceiversToCSV(String filename) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            // Write header
            String[] header = {"ID", "User ID", "Required Blood", "Required Organ", "Urgency", "Status", "Hospital ID", "Request Date", "Medical Condition"};
            writer.writeNext(header);
            
            // Write data
            List<com.blooddonation.model.Receiver> receivers = receiverDAO.getAllReceivers();
            for (com.blooddonation.model.Receiver receiver : receivers) {
                String[] row = {
                    String.valueOf(receiver.getId()),
                    String.valueOf(receiver.getUserId()),
                    receiver.getRequiredBloodGroup().getDisplayName(),
                    receiver.getRequiredOrgan().getDisplayName(),
                    receiver.getUrgencyLevel().getDisplayName(),
                    receiver.getStatus().getDisplayName(),
                    String.valueOf(receiver.getHospitalId()),
                    receiver.getRequestDate().toString(),
                    receiver.getMedicalCondition()
                };
                writer.writeNext(row);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting receivers to CSV: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Export system statistics to PDF
     * @param filename Output filename
     * @return true if successful, false otherwise
     */
    public boolean exportStatisticsToPDF(String filename) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new java.io.FileOutputStream(filename));
            document.open();
            
            // Add title
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Blood Donation Management System - Statistics Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Add date
            com.itextpdf.text.Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph date = new Paragraph("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            
            document.add(new Paragraph(" ")); // Empty line
            
            // Get statistics
            int[] donorStats = donorDAO.getDonorStatistics();
            int[] receiverStats = receiverDAO.getReceiverStatistics();
            int[] charityStats = charityDAO.getCharityStatistics();
            int hospitalCount = hospitalDAO.getHospitalCount();
            int totalUsers = userDAO.getAllUsers().size();
            
            // Create statistics table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            // Add statistics rows
            addTableRow(table, "Total Users", String.valueOf(totalUsers));
            addTableRow(table, "Total Donors", String.valueOf(donorStats[0]));
            addTableRow(table, "Available Donors", String.valueOf(donorStats[1]));
            addTableRow(table, "Unavailable Donors", String.valueOf(donorStats[2]));
            addTableRow(table, "Total Receivers", String.valueOf(receiverStats[0]));
            addTableRow(table, "Pending Requests", String.valueOf(receiverStats[1]));
            addTableRow(table, "Approved Requests", String.valueOf(receiverStats[2]));
            addTableRow(table, "Rejected Requests", String.valueOf(receiverStats[3]));
            addTableRow(table, "Fulfilled Requests", String.valueOf(receiverStats[4]));
            addTableRow(table, "Total Hospitals", String.valueOf(hospitalCount));
            addTableRow(table, "Total Charity Events", String.valueOf(charityStats[0]));
            addTableRow(table, "Active Events", String.valueOf(charityStats[1]));
            addTableRow(table, "Completed Events", String.valueOf(charityStats[2]));
            addTableRow(table, "Cancelled Events", String.valueOf(charityStats[3]));
            
            document.add(table);
            document.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error exporting statistics to PDF: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add a row to PDF table
     * @param table PDF table
     * @param label Row label
     * @param value Row value
     */
    private void addTableRow(PdfPTable table, String label, String value) {
        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        table.addCell(new com.itextpdf.text.pdf.PdfPCell(new Phrase(label, font)));
        table.addCell(new com.itextpdf.text.pdf.PdfPCell(new Phrase(value, font)));
    }
    
    /**
     * Show export dialog
     * @param parent Parent component
     */
    public void showExportDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Export Data", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Export options
        JLabel titleLabel = new JLabel("Select data to export:");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        
        JButton exportDonorsBtn = new JButton("Export Donors (CSV)");
        JButton exportReceiversBtn = new JButton("Export Receivers (CSV)");
        JButton exportStatsBtn = new JButton("Export Statistics (PDF)");
        JButton closeBtn = new JButton("Close");
        
        // Layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(exportDonorsBtn, gbc);
        gbc.gridx = 1;
        panel.add(exportReceiversBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(exportStatsBtn, gbc);
        gbc.gridx = 1;
        panel.add(closeBtn, gbc);
        
        // Event handlers
        exportDonorsBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("donors_export.csv"));
            if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                if (exportDonorsToCSV(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(dialog, "Donors data exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to export donors data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        exportReceiversBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("receivers_export.csv"));
            if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                if (exportReceiversToCSV(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(dialog, "Receivers data exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to export receivers data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        exportStatsBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("statistics_report.pdf"));
            if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                if (exportStatisticsToPDF(fileChooser.getSelectedFile().getAbsolutePath())) {
                    JOptionPane.showMessageDialog(dialog, "Statistics report exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to export statistics report.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        closeBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
