package com.bodms.database;

import com.bodms.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Migration {
    private static final Logger log = LoggerFactory.getLogger(Migration.class);

    public static void run() {
        // IMPORTANT: do not close the shared connection returned by Database.getConnection()
        Connection conn = Database.getConnection();
        try (Statement st = conn.createStatement()) {
            // users table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "role TEXT NOT NULL CHECK(role IN ('ADMIN','DONOR','RECEIVER','CHARITY'))" +
                    ")");

            // donors table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS donors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userId INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "bloodGroup TEXT, " +
                    "organ TEXT, " +
                    "location TEXT, " +
                    "contactInfo TEXT, " +
                    "availability INTEGER DEFAULT 1, " +
                    "approved INTEGER DEFAULT 0, " +
                    "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                    ")");

            // receivers table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS receivers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userId INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "bloodGroupNeeded TEXT, " +
                    "organNeeded TEXT, " +
                    "location TEXT, " +
                    "contactInfo TEXT, " +
                    "approved INTEGER DEFAULT 0, " +
                    "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                    ")");

            // charities table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS charities (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userId INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "purpose TEXT, " +
                    "contactInfo TEXT, " +
                    "funds REAL DEFAULT 0, " +
                    "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                    ")");

            // hospitals table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS hospitals (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "address TEXT, " +
                    "contact TEXT, " +
                    "linkedOrgans TEXT" +
                    ")");

            // charity campaigns
            st.executeUpdate("CREATE TABLE IF NOT EXISTS campaigns (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "charityId INTEGER NOT NULL, " +
                    "title TEXT NOT NULL, " +
                    "description TEXT, " +
                    "date TEXT, " +
                    "FOREIGN KEY(charityId) REFERENCES charities(id) ON DELETE CASCADE" +
                    ")");

            // Seed admin user if none exists (default password: admin123)
            try (PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE role='ADMIN'");
                 ResultSet rs = check.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String hashed = PasswordUtils.hash("admin123");
                    try (PreparedStatement ins = conn.prepareStatement("INSERT INTO users (name,email,password,role) VALUES (?,?,?,?)")) {
                        ins.setString(1, "Administrator");
                        ins.setString(2, "admin@bodms.local");
                        ins.setString(3, hashed);
                        ins.setString(4, "ADMIN");
                        ins.executeUpdate();
                    }
                }
            }

            log.info("Migrations completed");
        } catch (SQLException e) {
            log.error("Migration failed", e);
            throw new RuntimeException(e);
        }
    }
}
