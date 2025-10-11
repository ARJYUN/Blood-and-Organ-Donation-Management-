package com.donation.util;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing application theme and UI appearance
 */
public class ThemeUtil {
    private static final Logger LOGGER = Logger.getLogger(ThemeUtil.class.getName());
    
    // Theme colors
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Blue
    public static final Color SECONDARY_COLOR = new Color(231, 76, 60); // Red
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    public static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark Blue
    public static final Color ACCENT_COLOR = new Color(46, 204, 113); // Green
    
    // Font settings
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    /**
     * Set the application look and feel with custom theme
     */
    public static void setLookAndFeel() {
        try {
            // Use system look and feel as base
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Apply custom colors
            UIManager.put("Panel.background", new ColorUIResource(BACKGROUND_COLOR));
            UIManager.put("Button.background", new ColorUIResource(PRIMARY_COLOR));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
            UIManager.put("Button.select", new ColorUIResource(PRIMARY_COLOR.darker()));
            
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("TextField.foreground", TEXT_COLOR);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
            
            UIManager.put("ComboBox.background", Color.WHITE);
            UIManager.put("ComboBox.foreground", TEXT_COLOR);
            UIManager.put("ComboBox.selectionBackground", PRIMARY_COLOR);
            UIManager.put("ComboBox.selectionForeground", Color.WHITE);
            
            UIManager.put("Table.background", Color.WHITE);
            UIManager.put("Table.foreground", TEXT_COLOR);
            UIManager.put("Table.selectionBackground", PRIMARY_COLOR);
            UIManager.put("Table.selectionForeground", Color.WHITE);
            UIManager.put("Table.gridColor", new Color(220, 220, 220));
            
            UIManager.put("TabbedPane.selected", PRIMARY_COLOR);
            UIManager.put("TabbedPane.background", BACKGROUND_COLOR);
            UIManager.put("TabbedPane.contentAreaColor", BACKGROUND_COLOR);
            
            // Set global font
            setUIFont(new FontUIResource(REGULAR_FONT));
            
            LOGGER.info("Custom theme applied successfully");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to set custom look and feel", e);
            // Fall back to default look and feel
        }
    }
    
    /**
     * Set the default font for all UI components
     */
    public static void setUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
    
    /**
     * Create a styled button with consistent appearance
     */
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(REGULAR_FONT);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    /**
     * Create a styled panel with consistent appearance
     */
    public static JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }
    
    /**
     * Create a styled label with consistent appearance
     */
    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(REGULAR_FONT);
        return label;
    }
    
    /**
     * Create a styled header label with consistent appearance
     */
    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(HEADER_FONT);
        return label;
    }
    
    /**
     * Create a styled title label with consistent appearance
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(PRIMARY_COLOR);
        label.setFont(TITLE_FONT);
        return label;
    }
}