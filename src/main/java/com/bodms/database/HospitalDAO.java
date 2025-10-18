package com.bodms.database;

import com.bodms.model.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {
    private static final Logger log = LoggerFactory.getLogger(HospitalDAO.class);

    private Hospital map(ResultSet rs) throws SQLException {
        Hospital h = new Hospital();
        h.setId(rs.getInt("id"));
        h.setName(rs.getString("name"));
        h.setAddress(rs.getString("address"));
        h.setContact(rs.getString("contact"));
        h.setLinkedOrgans(rs.getString("linkedOrgans"));
        return h;
    }

    public Hospital create(Hospital h) throws SQLException {
        String sql = "INSERT INTO hospitals (name,address,contact,linkedOrgans) VALUES (?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getAddress());
            ps.setString(3, h.getContact());
            ps.setString(4, h.getLinkedOrgans());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) h.setId(keys.getInt(1));
            return h;
        }
    }

    public void update(Hospital h) throws SQLException {
        String sql = "UPDATE hospitals SET name=?, address=?, contact=?, linkedOrgans=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getAddress());
            ps.setString(3, h.getContact());
            ps.setString(4, h.getLinkedOrgans());
            ps.setInt(5, h.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM hospitals WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Hospital> findAll() {
        List<Hospital> list = new ArrayList<>();
        try (Statement st = Database.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM hospitals ORDER BY id DESC");
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findAll hospitals failed", e);
        }
        return list;
    }

    public List<Hospital> findNearby(String organType, String locationQuery) {
        List<Hospital> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT * FROM hospitals WHERE 1=1");
        if (organType != null && !organType.isBlank()) sb.append(" AND linkedOrgans LIKE ?");
        if (locationQuery != null && !locationQuery.isBlank()) sb.append(" AND (address LIKE ? OR name LIKE ?)");
        sb.append(" ORDER BY id DESC");
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sb.toString())) {
            int i = 1;
            if (organType != null && !organType.isBlank()) ps.setString(i++, "%" + organType + "%");
            if (locationQuery != null && !locationQuery.isBlank()) {
                ps.setString(i++, "%" + locationQuery + "%");
                ps.setString(i++, "%" + locationQuery + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findNearby failed", e);
        }
        return list;
    }
}
