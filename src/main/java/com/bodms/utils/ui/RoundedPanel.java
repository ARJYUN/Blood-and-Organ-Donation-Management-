package com.bodms.utils.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int arc = 20;
    private Color bg = new Color(0xE9E9E9);

    public RoundedPanel() { setOpaque(false); }

    public RoundedPanel(int arc, Color bg) {
        this.arc = arc; this.bg = bg; setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }
}
