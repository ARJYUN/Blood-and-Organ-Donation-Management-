package com.bodms.utils.ui;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color start = new Color(0xFF3A3A);
    private Color end = new Color(0xFF7A00);

    public GradientPanel() { setOpaque(false); }

    public GradientPanel(Color start, Color end) {
        this.start = start; this.end = end; setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, 40, 40);
        g2.dispose();
        super.paintComponent(g);
    }
}
