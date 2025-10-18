package com.bodms.utils.ui;

import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton {
    private Color start = new Color(0xFF3A3A);
    private Color end = new Color(0xFF7A00);

    public GradientButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(Font.BOLD, 16f));
        setMargin(new Insets(12, 24, 12, 24));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }
}
