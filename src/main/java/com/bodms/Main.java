package com.bodms;

import com.bodms.database.Database;
import com.bodms.database.Migration;
import com.bodms.view.WelcomeFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Ensure a consistent UI look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Initialize DB and run migrations
        Database.init();
        Migration.run();

        EventQueue.invokeLater(() -> {
            new WelcomeFrame().setVisible(true);
        });
    }
}
