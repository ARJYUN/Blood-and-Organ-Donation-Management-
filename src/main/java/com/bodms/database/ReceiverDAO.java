package com.bodms.database;

import com.bodms.model.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiverDAO {
    private static final Logger log = LoggerFactory.getLogger(ReceiverDAO.class);

    private Receiver map(ResultSet rs) throws SQLException {
        Receiver r = new Receiver();
        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("userId"));
        r.setName(rs.getString("name"));
        r.setBloodGroupNeeded(rs.getString("bloodGroupNeeded"));
        r.setOrganNeeded(rs.getString("organNeeded"));
        r.setLocation(rs.getString("location"));
        r.setContactInfo(rs.getString("contactInfo"));
        r.setApproved(rs.getInt("approved") == 1);
        return r;
    }

    public Receiver create(Receiver r) throws SQLException {
        String sql = "INSERT INTO receivers (userId,name,bloodGroupNeeded,organNeeded,location,contactInfo,approved) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getName());
            ps.setString(3, r.getBloodGroupNeeded());
            ps.setString(4, r.getOrganNeeded());
            ps.setString(5, r.getLocation());
            ps.setString(6, r.getContactInfo());
            ps.setInt(7, r.isApproved()?1:0);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) r.setId(keys.getInt(1));
            return r;
        }
    }

    public void update(Receiver r) throws SQLException {
        String sql = "UPDATE receivers SET name=?, bloodGroupNeeded=?, organNeeded=?, location=?, contactInfo=?, approved=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getBloodGroupNeeded());
            ps.setString(3, r.getOrganNeeded());
            ps.setString(4, r.getLocation());
            ps.setString(5, r.getContactInfo());
            ps.setInt(6, r.isApproved()?1:0);
            ps.setInt(7, r.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM receivers WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Receiver> findAll() {
        List<Receiver> list = new ArrayList<>();
        try (Statement st = Database.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM receivers ORDER BY id DESC");
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findAll receivers failed", e);
        }
        return list;
    }

    public List<Receiver> findByUserId(int userId) {
        List<Receiver> list = new ArrayList<>();
        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM receivers WHERE userId=?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findByUserId receivers failed", e);
        }
        return list;
    }
}
