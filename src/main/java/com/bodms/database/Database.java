package com.bodms.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private static Connection connection;
    private static Path dbPath;

    public static void init() {
        try {
            Path dataDir = Paths.get(System.getProperty("user.dir"), "data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            dbPath = dataDir.resolve("donation.db");
            String url = "jdbc:sqlite:" + dbPath.toAbsolutePath();
            connection = DriverManager.getConnection(url);
            log.info("Connected to SQLite DB at {}", dbPath);
        } catch (Exception e) {
            log.error("Failed to initialize database", e);
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Database not initialized. Call Database.init() first.");
        }
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                log.info("Closed database connection");
            } catch (SQLException e) {
                log.warn("Error closing DB connection", e);
            }
        }
    }
}
